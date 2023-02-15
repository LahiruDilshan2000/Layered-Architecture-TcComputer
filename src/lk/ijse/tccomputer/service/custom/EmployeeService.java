package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.EmployeeDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface EmployeeService extends SuperService {

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    void deleteEmployee(String pk);

    String getNextEmployeeId();

    List<EmployeeDTO> findAllEmployee();

    List<EmployeeDTO> searchEmployeeByText(String text);

    long getEmployeeCount();
}
