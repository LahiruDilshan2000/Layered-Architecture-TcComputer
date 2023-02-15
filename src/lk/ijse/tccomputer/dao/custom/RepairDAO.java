package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Repair;

import java.sql.Date;
import java.util.Optional;

public interface RepairDAO extends CrudDAO<Repair,String> {

    Optional<String> getNextPk();

    long getTodayRepair(Date date);
}
