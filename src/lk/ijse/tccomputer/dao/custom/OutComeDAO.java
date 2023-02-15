package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.OutCome;

import java.util.List;

public interface OutComeDAO extends CrudDAO<OutCome,String > {

    List<OutCome> getYears();

    List<OutCome> getMonthlyOutCome(int month, int year);

}
