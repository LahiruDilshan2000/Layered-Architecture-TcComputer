package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.EmpSalaryDetailDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.EmpSalaryDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpSalaryDetailDAOImpl implements EmpSalaryDetailDAO {
    @Override
    public EmpSalaryDetail save(EmpSalaryDetail empSalaryDetail) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO EmpSalaryDetail VALUES(?,?)", empSalaryDetail.getEmpID(), empSalaryDetail.getSalary()))
                throw new SQLException("Failed to save the employee salary");
            return empSalaryDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public EmpSalaryDetail update(EmpSalaryDetail empSalaryDetail) {

        try {
            if (!DBUtil.executeUpdate("UPDATE EmpSalaryDetail SET salary=? WHERE empID=?", empSalaryDetail.getSalary(), empSalaryDetail.getEmpID()))
                throw new SQLException("Failed to update the employee salary");
            return empSalaryDetail;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM EmpSalaryDetail WHERE empID=?", pk))
                throw new SQLException("Failed to delete the employee salary");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<EmpSalaryDetail> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM EmpSalaryDetail");
            return getEmployeeSalaryList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load employee salary");
        }
    }

    private List<EmpSalaryDetail> getEmployeeSalaryList(ResultSet rst) {

        try {
            List<EmpSalaryDetail> employeeList = new ArrayList<>();
            while (rst.next()) {
                employeeList.add(new EmpSalaryDetail(rst.getString("empID"), rst.getDouble("salary")));
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existByPk(String pk) {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM EmpSalaryDetail WHERE empID=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
