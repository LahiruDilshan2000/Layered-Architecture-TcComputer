package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.EmployeeDAO;
import lk.ijse.tccomputer.dao.exception.ConstraintViolationException;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public Employee save(Employee employee) {

        try {
            if (!DBUtil.executeUpdate("INSERT INTO Employee VALUES(?,?,?,?)", employee.getEmpID(), employee.getName(), employee.getMail(), employee.getContact()))
                throw new SQLException("Failed to save the employee");
            return employee;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Employee update(Employee employee) {

        try {
            if (!DBUtil.executeUpdate("UPDATE Employee SET name=?,mail=?,contact=? WHERE empID=?", employee.getName(), employee.getMail(), employee.getContact(), employee.getEmpID()))
                throw new SQLException("Failed to update the employee");
            return employee;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public void delete(String pk) {

        try {
            if (!DBUtil.executeUpdate("DELETE FROM Employee WHERE empID=?", pk))
                throw new SQLException("Failed to delete the employee");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Employee> findAll() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM Employee");
            return getEmployeeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load employee");
        }
    }

    @Override
    public Optional<String> getNextPk() {

        try {
            ResultSet rst = DBUtil.executeQuery("SELECT empID FROM Employee ORDER BY empID DESC LIMIT 1");
            return rst.next() ? Optional.of(rst.getString("empID")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Long> count() {

        try {
            ResultSet rst = DBUtil.executeQuery("select COUNT(empID) AS count from Employee");
            return rst.next() ? Optional.of(rst.getLong(1)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Employee> getEmployeeList(ResultSet rst) {

        try {
            List<Employee> employeeList = new ArrayList<>();
            while (rst.next()) {
                employeeList.add(new Employee(rst.getString("empID"), rst.getString("name"), rst.getString("mail"), rst.getString("contact")));
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
