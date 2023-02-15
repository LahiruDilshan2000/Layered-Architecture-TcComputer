package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.Orders;

import java.sql.Date;
import java.util.Optional;

public interface OrdersDAO extends CrudDAO<Orders,String > {

    Optional<String> getNextPk();

    long getTodayOrdersCount(Date date);
}
