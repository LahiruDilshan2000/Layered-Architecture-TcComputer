package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.SuperDAO;
import lk.ijse.tccomputer.dto.EmployeeDTO;

import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<EmployeeDTO> getAllEmployeeWithSalary();

    List<EmployeeDTO> searchByText(String text);
}
