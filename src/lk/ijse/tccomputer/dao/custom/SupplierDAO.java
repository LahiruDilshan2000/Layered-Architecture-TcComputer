package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierDAO extends CrudDAO<Supplier,String > {

    List<Supplier> searchByText(String text);

    Optional<String> getNextPk();
}
