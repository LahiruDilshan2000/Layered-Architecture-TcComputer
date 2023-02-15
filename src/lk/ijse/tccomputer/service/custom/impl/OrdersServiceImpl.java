package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.IncomeDAO;
import lk.ijse.tccomputer.dao.custom.ItemDAO;
import lk.ijse.tccomputer.dao.custom.OrderDetailDAO;
import lk.ijse.tccomputer.dao.custom.OrdersDAO;
import lk.ijse.tccomputer.db.DBConnection;
import lk.ijse.tccomputer.dto.OrderDetailDTO;
import lk.ijse.tccomputer.dto.OrdersDTO;
import lk.ijse.tccomputer.service.custom.OrdersService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdersServiceImpl implements OrdersService {

    private final Connection connection;
    private final OrdersDAO ordersDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final ItemDAO itemDAO;
    private final IncomeDAO incomeDAO;
    private final Convertor convertor;

    public OrdersServiceImpl() {

        connection = DBConnection.getInstance().getConnection();
        ordersDAO = DaoFactory.getInstance().getDAO(DaoType.ORDERS);
        orderDetailDAO = DaoFactory.getInstance().getDAO(DaoType.ORDERDETAIL);
        itemDAO = DaoFactory.getInstance().getDAO(DaoType.ITEM);
        incomeDAO = DaoFactory.getInstance().getDAO(DaoType.INCOME);
        convertor = new Convertor();
    }

    @Override
    public OrdersDTO saveOrder(OrdersDTO ordersDTO) {

        try {
            connection.setAutoCommit(false);

            if (ordersDAO.save(convertor.toOrders(ordersDTO)) == null)
                throw new RuntimeException("Failed to save order !");

            for (OrderDetailDTO orderDetailDTO : ordersDTO.getOrderDetailList()) {
                if (orderDetailDAO.save(convertor.toOrderDetail(orderDetailDTO)) == null)
                    throw new RuntimeException("Failed to save order details !");
            }

            for (OrderDetailDTO orderDetailDTO : ordersDTO.getOrderDetailList()) {
                if (itemDAO.reducesItemQty(convertor.toItem(orderDetailDTO.getItemDTO())) == null)
                    throw new RuntimeException("Failed to reduces item !");
            }

            if (incomeDAO.save(convertor.toIncome(ordersDTO.getIncomeDTO())) == null)
                throw new RuntimeException("Failed to save income detail !");

            connection.commit();

            return ordersDTO;

        } catch (Throwable t) {
            try {
                connection.rollback();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getNextOrderId() {

        Optional<String> nextPk = ordersDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;

    }

    @Override
    public long getTodayOrderCount() {

        List<OrdersDTO> tmList = ordersDAO.findAll().stream().filter(orders -> orders.getDate().equals(Date.valueOf(LocalDate.now()))).map(orders -> convertor.fromOrder(orders)).collect(Collectors.toList());
        return tmList.size();
    }
}
