package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Supplies;

import java.util.Optional;

public interface SuppliesDAO extends CrudDAO<Supplies,String > {

    Optional<String> getNextPk();
}
