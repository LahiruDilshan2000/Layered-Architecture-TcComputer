package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.EmpSalaryDetailDAO;
import lk.ijse.tccomputer.dao.custom.EmployeeDAO;
import lk.ijse.tccomputer.dao.custom.QueryDAO;
import lk.ijse.tccomputer.db.DBConnection;
import lk.ijse.tccomputer.dto.EmployeeDTO;
import lk.ijse.tccomputer.service.custom.EmployeeService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final Connection connection;
    private final EmployeeDAO employeeDAO;
    private final EmpSalaryDetailDAO empSalaryDetailDAO;
    private final QueryDAO queryDAO;
    private final Convertor convertor;

    public EmployeeServiceImpl(){
        connection= DBConnection.getInstance().getConnection();
        employeeDAO= DaoFactory.getInstance().getDAO(DaoType.EMPLOYEE);
        empSalaryDetailDAO=DaoFactory.getInstance().getDAO(DaoType.EMPSALARYDETAIL);
        queryDAO=DaoFactory.getInstance().getDAO(DaoType.QUERY);
        convertor=new Convertor();
    }
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        try {
            connection.setAutoCommit(false);
            if (employeeDAO.save(convertor.toEmployee(employeeDTO))==null)throw new RuntimeException("failed th save employee");

            if(empSalaryDetailDAO.save(convertor.toEmpSalaryDetail(employeeDTO))==null)throw new RuntimeException("failed th save employee salary detail");

            connection.commit();

            return employeeDTO;

        }catch (Throwable t){
            try {
                connection.rollback();
                return null;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        try {
            connection.setAutoCommit(false);
            if (employeeDAO.update(convertor.toEmployee(employeeDTO))==null)throw new RuntimeException("failed th update employee");

            if(empSalaryDetailDAO.update(convertor.toEmpSalaryDetail(employeeDTO))==null)throw new RuntimeException("failed th update employee salary detail");

            connection.commit();

            return employeeDTO;

        }catch (Throwable t){
            try {
                connection.rollback();
                return null;
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteEmployee(String pk) {
        try {
            connection.setAutoCommit(false);

            if(!empSalaryDetailDAO.existByPk(pk))throw new RuntimeException("Employee not found");

            empSalaryDetailDAO.delete(pk);

            employeeDAO.delete(pk);

            connection.commit();

        }catch (Throwable t){
            try {
                connection.rollback();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getNextEmployeeId() {

        Optional<String> nextPk = employeeDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;

    }

    @Override
    public List<EmployeeDTO> findAllEmployee() {

        return queryDAO.getAllEmployeeWithSalary();

    }

    @Override
    public List<EmployeeDTO> searchEmployeeByText(String text) {

        return queryDAO.searchByText(text);

    }

    @Override
    public long getEmployeeCount() {

        return employeeDAO.findAll().size();
    }
}
