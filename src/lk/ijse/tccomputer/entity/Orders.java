package lk.ijse.tccomputer.entity;

import java.sql.Date;

public class Orders implements SuperEntity{
    private String orderID;
    private Date date;
    private String customerID;

    @Override
    public String toString() {
        return "Orders{" +
                "orderID='" + orderID + '\'' +
                ", date=" + date +
                ", customerID='" + customerID + '\'' +
                '}';
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

    public Orders(String orderID, Date date, String customerID) {
        this.orderID = orderID;
        this.date = date;
        this.customerID = customerID;
    }

    public Orders() {
    }
}
