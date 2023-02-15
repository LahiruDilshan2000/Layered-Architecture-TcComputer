package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.IncomeDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAOImpl implements IncomeDAO {
    @Override
    public Income save(Income income) {

        try {
            if (!DBUtil.executeUpdate("Insert into Income VALUES(?,?,?,?,?)", income.getIncomeID(), income.getDay(), income.getMonth(), income.getYear(), income.getTotal()))
                throw new SQLException("Failed to save the income");
            return income;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Income update(Income income) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Income SET day=?, month=?, year=?, total=? WHERE incomeID=?", income.getDay(), income.getMonth(), income.getYear(), income.getTotal(), income.getIncomeID()))
                throw new SQLException("Failed to update the income");
            return income;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Income WHERE incomeID=?", pk))
                throw new SQLException("Failed to delete the income");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Income> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Income ORDER BY date ASC");
            return getIncomeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load income");
        }
    }

    private List<Income> getIncomeList(ResultSet rst) {

        try {
            List<Income> incomeList = new ArrayList<>();
            while (rst.next()) {
                incomeList.add(new Income(rst.getString("incomeID"), rst.getInt("date"), rst.getInt("month"), rst.getInt("year"), rst.getDouble("total")));
            }
            return incomeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Income> getYear() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Income GROUP BY year");
            return getIncomeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Income> getMonthlyIncome(int year, int month) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT incomeID,date,month,year,SUM(total) AS total FROM Income WHERE year=? && month=? GROUP BY date ORDER BY date ASC", year, month);
            return getIncomeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
