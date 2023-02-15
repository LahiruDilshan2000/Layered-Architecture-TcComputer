package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.SuppliesDetailDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.SuppliesDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliesDetailDAOImpl implements SuppliesDetailDAO {
    @Override
    public SuppliesDetail save(SuppliesDetail suppliesDetail) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO SuppliesDetail VALUES(?,?,?,?)", suppliesDetail.getSuppliesCode(), suppliesDetail.getItemCode(), suppliesDetail.getQty(), suppliesDetail.getUnitPrice()))
                throw new SQLException("Failed to save the supplies detail");
            return suppliesDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public SuppliesDetail update(SuppliesDetail suppliesDetail) {

        try {
            if (!DBUtil.executeUpdate("UPDATE SuppliesDetail SET itemCode=?, qty=?, unitPrice=? WHERE suppliesCode=?", suppliesDetail.getItemCode(), suppliesDetail.getQty(), suppliesDetail.getUnitPrice(), suppliesDetail.getSuppliesCode()))
                throw new SQLException("Failed to update the supplies detail");
            return suppliesDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM SuppliesDetail WHERE suppliesCode=?", pk))
                throw new SQLException("Failed to delete the supplies detail");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<SuppliesDetail> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM SuppliesDetail");
            return getSuppliesDetailList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load supplies detail");
        }
    }

    private List<SuppliesDetail> getSuppliesDetailList(ResultSet rst) {

        try {
            List<SuppliesDetail> suppliesDetailList = new ArrayList<>();
            while (rst.next()) {
                suppliesDetailList.add(new SuppliesDetail(rst.getString("suppliesCode"), rst.getString("itemCode"), rst.getInt("qty"), rst.getDouble("unitPrice")));
            }
            return suppliesDetailList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
