package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Employee;

import java.util.Optional;

public interface EmployeeDAO extends CrudDAO<Employee,String> {

    Optional<String> getNextPk();

    Optional<Long> count();
}
