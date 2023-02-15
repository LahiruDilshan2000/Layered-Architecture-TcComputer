package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO extends CrudDAO<Customer,String>{

    List<Customer> searchByText(String text);

    Optional<String> getNextPk();

    Optional<Long> count();
}
