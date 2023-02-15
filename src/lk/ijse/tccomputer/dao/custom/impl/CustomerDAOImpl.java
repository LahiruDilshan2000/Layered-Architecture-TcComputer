package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.CustomerDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Customer save(Customer customer) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?)", customer.getId(), customer.getName(), customer.getAddress(), customer.getContact()))
                throw new SQLException("Failed to save the customer");
            return customer;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Customer update(Customer customer) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Customer SET name=?,address=?,contact=? WHERE id=?", customer.getName(), customer.getAddress(), customer.getContact(), customer.getId()))
                throw new SQLException("Failed to update the customer");
            return customer;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Customer WHERE id=?", pk))
                throw new SQLException("Failed to delete the customer");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Customer> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Customer");
            return getCustomerList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load customer");
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("id")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Long> count() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(id) AS count FROM Customer");
            return rst.next() ? Optional.of(rst.getLong(1)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> searchByText(String text) {

        try {
            text = "%" + text + "%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ? OR contact LIKE ?", text, text, text, text);
            return getCustomerList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Customer> getCustomerList(ResultSet rst) {

        try {
            List<Customer> customerList = new ArrayList<>();
            while (rst.next()) {
                customerList.add(new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact")));
            }
            return customerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
