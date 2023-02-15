package lk.ijse.tccomputer.entity;

import java.sql.Date;

public class Repair implements SuperEntity{
    private String repairID;
    private String itemType;
    private String itemName;
    private Date date;
    private double amount;
    private String customerID;

    @Override
    public String toString() {
        return "Repair{" +
                "repairID='" + repairID + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemName='" + itemName + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", customerID='" + customerID + '\'' +
                '}';
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Repair(String repairID, String itemType, String itemName, Date date, double amount, String customerID) {
        this.repairID = repairID;
        this.itemType = itemType;
        this.itemName = itemName;
        this.date = date;
        this.amount = amount;
        this.customerID = customerID;
    }

    public Repair() {
    }
}
