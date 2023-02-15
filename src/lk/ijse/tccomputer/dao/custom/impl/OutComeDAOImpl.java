package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.OutComeDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.OutCome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutComeDAOImpl implements OutComeDAO {
    @Override
    public OutCome save(OutCome outCome) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO OutCome VALUES(?,?,?,?,?)", outCome.getOutcomeID(), outCome.getDay(), outCome.getMonth(), outCome.getYear(), outCome.getTotal()))
                throw new SQLException("Failed to save the outcome");
            return outCome;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public OutCome update(OutCome outCome) {

        try {
            if (!DBUtil.executeUpdate("UPDATE OutCome SET day=?, month=?, year=?, total=? WHERE outcomeID=?", outCome.getDay(), outCome.getMonth(), outCome.getYear(), outCome.getTotal(), outCome.getOutcomeID()))
                throw new SQLException("Failed to update the outcome");
            return outCome;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM OutCome WHERE outcomeID=?", pk))
                throw new SQLException("Failed to delete the outcome");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<OutCome> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM OutCome");
            return getOutComeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load outcome");
        }
    }

    @Override
    public List<OutCome> getYears() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Outcome GROUP BY year");
            return getOutComeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OutCome> getMonthlyOutCome(int month, int year) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT outcomeID,date,month,year,SUM(total) AS total FROM Outcome WHERE year=? && month=? GROUP BY date ORDER BY date ASC", month, year);
            return getOutComeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<OutCome> getOutComeList(ResultSet rst) {

        try {
            List<OutCome> outComeList = new ArrayList<>();
            while (rst.next()) {
                outComeList.add(new OutCome(rst.getString("outcomeID"), rst.getInt("date"), rst.getInt("month"), rst.getInt("year"), rst.getDouble("total")));
            }
            return outComeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
