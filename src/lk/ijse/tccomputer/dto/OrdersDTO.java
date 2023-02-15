package lk.ijse.tccomputer.dto;


import java.sql.Date;
import java.util.List;

public class OrdersDTO {
    private String orderID;
    private Date date;
    private String customerID;
    private List<OrderDetailDTO> orderDetailList;
    private IncomeDTO incomeDTO;

    public OrdersDTO() {
    }

    public OrdersDTO(String orderID, Date date, String customerID) {
        this.orderID = orderID;
        this.date = date;
        this.customerID = customerID;
    }

    public OrdersDTO(String orderID, Date date, String customerID, List<OrderDetailDTO> orderDetailList, IncomeDTO incomeDTO) {
        this.orderID = orderID;
        this.date = date;
        this.customerID = customerID;
        this.orderDetailList = orderDetailList;
        this.incomeDTO = incomeDTO;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<OrderDetailDTO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public IncomeDTO getIncomeDTO() {
        return incomeDTO;
    }

    public void setIncomeDTO(IncomeDTO incomeDTO) {
        this.incomeDTO = incomeDTO;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "orderID='" + orderID + '\'' +
                ", date=" + date +
                ", customerID='" + customerID + '\'' +
                ", orderDetailList=" + orderDetailList +
                ", incomeDTO=" + incomeDTO +
                '}';
    }
}
