package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.ItemDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public Item save(Item item) {

        try {
            if (!DBUtil.executeUpdate("insert into Item values(?,?,?,?,?)", item.getItemCode(), item.getBrand(), item.getName(), item.getQtyOnHand(), item.getUnitPrice()))
                throw new SQLException("Failed to save the item");
            return item;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Item update(Item item) {

        try {
            if (!DBUtil.executeUpdate("update Item set brand=?,name=?,qtyOnHand=?,unitPrice=? where itemCode=?", item.getBrand(), item.getName(), item.getQtyOnHand(), item.getUnitPrice(), item.getItemCode()))
                throw new SQLException("Failed to update the item");
            return item;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("delete from Item where itemCode=?", pk))
                throw new SQLException("Failed to delete the item");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Item> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Item");
            return getItemList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load item");
        }
    }

    @Override
    public List<Item> searchByText(String text) {

        try {
            text = "%" + text + "%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Item WHERE itemCode LIKE ? OR brand LIKE ? OR name LIKE ? OR qtyOnHand LIKE ? OR unitPrice LIKE ?", text, text, text, text, text);
            return getItemList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT itemCode FROM Item order by itemCode DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("itemCode")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item reducesItemQty(Item item) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Item SET qtyOnHand=qtyOnHand-? WHERE itemCode=?", item.getQtyOnHand(), item.getItemCode()))
                throw new SQLException("Failed to reduce items !");
            return item;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Item updateItemQty(Item item) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Item SET qtyOnHand=qtyOnHand+? WHERE itemCode=?", item.getQtyOnHand(), item.getItemCode()))
                throw new SQLException("Failed to update item !");
            return item;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Item> getRemainingItem() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Item WHERE qtyOnHand<20");
            return getItemList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Item> getItemList(ResultSet rst) {

        try {
            List<Item> itemList = new ArrayList<>();
            while (rst.next()) {
                itemList.add(new Item(rst.getString("itemCode"), rst.getString("brand"), rst.getString("name"), rst.getInt("qtyOnHand"), rst.getDouble("unitPrice")));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
