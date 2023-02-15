package lk.ijse.tccomputer.dao.custom.impl;

import lk.ijse.tccomputer.dao.custom.QueryDAO;
import lk.ijse.tccomputer.dao.util.DBUtil;
import lk.ijse.tccomputer.dto.EmployeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<EmployeeDTO> getAllEmployeeWithSalary() {

        try{
            ResultSet rst = DBUtil.executeQuery("SELECT Employee.empID,Employee.name,Employee.mail,Employee.contact,EmpSalaryDetail.salary FROM Employee INNER JOIN EmpSalaryDetail ON Employee.empID = EmpSalaryDetail.empID");
            return getEmployeeList(rst);
        }catch (SQLException e){
            throw new RuntimeException("Failed to load employee");
        }
    }

    @Override
    public List<EmployeeDTO> searchByText(String text) {

        try {
            text="%"+text+"%";
            ResultSet rst = DBUtil.executeQuery("SELECT Employee.empID,Employee.name,Employee.mail,Employee.contact,EmpSalaryDetail.salary FROM Employee INNER JOIN EmpSalaryDetail ON Employee.empID = EmpSalaryDetail.empID WHERE Employee.empID LIKE ? OR Employee.name LIKE ? OR Employee.mail LIKE ? OR Employee.contact Like ? OR EmpSalaryDetail.salary LIKE ?", text, text, text, text, text);
            return getEmployeeList(rst);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private List<EmployeeDTO> getEmployeeList(ResultSet rst) {

        try{
            List<EmployeeDTO> employeeList=new ArrayList<>();
            while (rst.next()){
                employeeList.add(new EmployeeDTO(rst.getString("empID"),rst.getString("name"), rst.getString("mail"), rst.getString("contact"), rst.getDouble("salary")));
            }
            return employeeList;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
