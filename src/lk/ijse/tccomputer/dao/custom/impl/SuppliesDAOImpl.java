package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.SuppliesDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Supplies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuppliesDAOImpl implements SuppliesDAO {
    @Override
    public Supplies save(Supplies supplies) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Supplies VALUES(?,?,?)", supplies.getSuppliesCode(), supplies.getDate(), supplies.getSupplierID()))
                throw new SQLException("Failed to save the supplies");
            return supplies;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Supplies update(Supplies supplies) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Supplies SET date=?, supplierID=? WHERE suppliesCode=?", supplies.getDate(), supplies.getSupplierID(), supplies.getSuppliesCode()))
                throw new SQLException("Failed to update the supplies");
            return supplies;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Supplies WHERE suppliesCode=?", pk))
                throw new SQLException("Failed to delete the supplies");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Supplies> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Supplies");
            return getSuppliesList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load supplies");
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT suppliesCode FROM Supplies ORDER BY suppliesCode DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("suppliesCode")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Supplies> getSuppliesList(ResultSet rst) {

        try {
            List<Supplies> suppliesList = new ArrayList<>();
            while (rst.next()) {
                suppliesList.add(new Supplies(rst.getString("suppliesCode"), rst.getDate("date"), rst.getString("supplierID")));
            }
            return suppliesList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
