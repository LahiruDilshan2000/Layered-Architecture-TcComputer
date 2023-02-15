package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.UserDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    @Override
    public User save(User user) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO User VALUES(?,?,?)", user.getUserID(), user.getPassword(), user.getRole()))
                throw new SQLException("Failed to save the user");
            return user;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public User update(User user) {

        try {
            if (!DBUtil.executeUpdate("UPDATE User SET password=? WHERE userID=?", user.getPassword(), user.getUserID()))
                throw new SQLException("Failed to update the user");
            return user;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk){

        try {
            if (!DBUtil.executeUpdate("DELETE FROM User WHERE userID=?", pk))
                throw new SQLException("Failed to delete the user");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<User> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM User");
            return getUserList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load user");
        }
    }

    @Override
    public boolean existByPk(String pk) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM User WHERE userID=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getUser(String userID) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM User WHERE userID= ?", userID);
            return rst.next() ? Optional.of(new User(rst.getString("userID"), rst.getString("password"), rst.getString("role"))) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> getUserList(ResultSet rst) {

        try {
            List<User> userList = new ArrayList<>();
            while (rst.next()) {
                userList.add(new User(rst.getString("userID"), rst.getString("password"), rst.getString("role")));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
