package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.OrdersDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Orders;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public Orders save(Orders orders) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Orders VALUES(?,?,?)", orders.getOrderID(), orders.getDate(), orders.getCustomerID()))
                throw new SQLException("Failed to save the order");
            return orders;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Orders update(Orders orders) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Orders SET date=?, customerID=? WHERE orderID=?", orders.getDate(), orders.getCustomerID(), orders.getOrderID()))
                throw new SQLException("Failed to update the order");
            return orders;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Orders WHERE orderID=?", pk))
                throw new SQLException("Failed to delete the order");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Orders> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Orders");
            return getOrdersList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load order");
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT orderID FROM Orders ORDER BY orderID DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("orderID")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getTodayOrdersCount(Date date) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(orderID) FROM Orders WHERE date=?", date);
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Orders> getOrdersList(ResultSet rst) {

        try {
            List<Orders> ordersList = new ArrayList<>();
            while (rst.next()) {
                ordersList.add(new Orders(rst.getString("orderID"), rst.getDate("date"), rst.getString("customerID")));
            }
            return ordersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
