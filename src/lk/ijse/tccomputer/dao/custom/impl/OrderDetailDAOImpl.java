package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.OrderDetailDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public OrderDetail save(OrderDetail orderDetail) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO OrderDetail VALUES(?,?,?,?)", orderDetail.getOrderID(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice()))
                throw new SQLException("Failed to save the order detail");
            return orderDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail) {

        try {
            if (!DBUtil.executeUpdate("UPDATE OrderDetail SET itemCode=?, qty=?, unitPrice=? where orderID=? AND itemCode= ?", orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice(), orderDetail.getOrderID(), orderDetail.getItemCode()))
                throw new SQLException("Failed to update the order detail");
            return orderDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM OrderDetail WHERE orderID=? AND itemCode=?", pk))
                throw new SQLException("Failed to delete the order detail");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<OrderDetail> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM  OrderDetail");
            return getOrderDetailList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load order detail");
        }
    }

    private List<OrderDetail> getOrderDetailList(ResultSet rst) {

        try {
            List<OrderDetail> orderDetailList = new ArrayList<>();
            while (rst.next()) {
                orderDetailList.add(new OrderDetail(rst.getString("orderID"), rst.getString("itemCode"), rst.getInt("qty"), rst.getDouble("unitPrice")));
            }
            return orderDetailList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
