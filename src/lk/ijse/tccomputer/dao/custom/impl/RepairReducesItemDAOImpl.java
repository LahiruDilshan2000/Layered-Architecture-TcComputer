package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.RepairReducesItemDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.RepairReducesItemDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairReducesItemDAOImpl implements RepairReducesItemDAO {
    @Override
    public RepairReducesItemDetail save(RepairReducesItemDetail repairReducesItemDetail){

        try {
            if (!DBUtil.executeUpdate("INSERT INTO ReducesItemDetail VALUES(?,?,?,?)", repairReducesItemDetail.getRepairID(), repairReducesItemDetail.getItemCode(), repairReducesItemDetail.getQty(), repairReducesItemDetail.getUnitPrice()))
                throw new SQLException("Failed to save the reduces item detail");
            return repairReducesItemDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public RepairReducesItemDetail update(RepairReducesItemDetail repairReducesItemDetail){

        try {
            if (!DBUtil.executeUpdate("UPDATE ReducesItemDetail SET itemCode=?, qty=?, unitPrice=? WHERE repairID=?", repairReducesItemDetail.getItemCode(), repairReducesItemDetail.getQty(), repairReducesItemDetail.getUnitPrice(), repairReducesItemDetail.getRepairID()))
                throw new SQLException("Failed to update the reduces item detail");
            return repairReducesItemDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM ReducesItemDetail WHERE repairID=?", pk))
                throw new SQLException("Failed to delete the reduces item detail");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<RepairReducesItemDetail> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM ReducesItemDetail");
            return getReducesItemList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load reduces item detail");
        }
    }

    private List<RepairReducesItemDetail> getReducesItemList(ResultSet rst) {

        try {
            List<RepairReducesItemDetail> repairReducesItemDetailList = new ArrayList<>();
            while (rst.next()) {
                repairReducesItemDetailList.add(new RepairReducesItemDetail(rst.getString("repairID"), rst.getString("itemCode"), rst.getInt("qty"), rst.getDouble("unitPrice")));
            }
            return repairReducesItemDetailList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
