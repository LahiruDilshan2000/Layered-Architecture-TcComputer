package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.SupplierDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public Supplier save(Supplier supplier) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Supplier VALUES(?,?,?,?)", supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getContact()))
                throw new SQLException("Failed to save the supplier");
            return supplier;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Supplier update(Supplier supplier) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Supplier SET name=?, address=?, contact=? WHERE id=?", supplier.getName(), supplier.getAddress(), supplier.getContact(), supplier.getId()))
                throw new SQLException("Failed to update the supplier");
            return supplier;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Supplier WHERE id=?", pk))
                throw new SQLException("Failed to delete the supplier");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Supplier> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Supplier");
            return getSupplierList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load supplier");
        }
    }

    @Override
    public List<Supplier> searchByText(String text) {

        try {
            text = "%" + text + "%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Supplier WHERE id LIKE ? OR name LIKE ? OR address LIKE ? OR contact LIKE ?", text, text, text, text);
            return getSupplierList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT id FROM Supplier ORDER BY id DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("id")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Supplier> getSupplierList(ResultSet rst) {

        try {
            List<Supplier> supplierList = new ArrayList<>();
            while (rst.next()) {
                supplierList.add(new Supplier(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getString("contact")));
            }
            return supplierList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
