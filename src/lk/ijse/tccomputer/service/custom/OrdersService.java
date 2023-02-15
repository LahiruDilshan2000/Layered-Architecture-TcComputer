package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.OrdersDTO;
import lk.ijse.tccomputer.service.SuperService;


public interface OrdersService extends SuperService {

    OrdersDTO saveOrder(OrdersDTO ordersDTO);

    String getNextOrderId();

    long getTodayOrderCount();
}
