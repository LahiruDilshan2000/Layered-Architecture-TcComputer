package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.RepairDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Repair;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepairDAOImpl implements RepairDAO {

    @Override
    public Repair save(Repair repair) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Repair VALUES(?,?,?,?,?,?)", repair.getRepairID(), repair.getItemType(), repair.getItemName(), repair.getDate(), repair.getAmount(), repair.getCustomerID()))
                throw new SQLException("Failed to save the repair");
            return repair;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Repair update(Repair repair) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Repair SET itemType=?, itemName=?, date=?, amount=?, customerID=? WHERE repairID=?", repair.getItemType(), repair.getItemName(), repair.getDate(), repair.getAmount(), repair.getCustomerID(), repair.getRepairID()))
                throw new SQLException("Failed to update the repair");
            return repair;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Repair WHERE repairID=?", pk))
                throw new SQLException("Failed to delete the repair");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Repair> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Repair");
            return getRepairList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load repair");
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT repairID FROM Repair ORDER BY repairID DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("repairID")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getTodayRepair(Date date) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT COUNT(repairID) FROM Repair WHERE date=?", date);
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Repair> getRepairList(ResultSet rst) {

        try {
            List<Repair> repairList = new ArrayList<>();
            while (rst.next()) {
                repairList.add(new Repair(rst.getString("repairID"), rst.getString("itemType"), rst.getString("itemName"), rst.getDate("date"), rst.getDouble("amount"), rst.getString("customerID")));
            }
            return repairList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
