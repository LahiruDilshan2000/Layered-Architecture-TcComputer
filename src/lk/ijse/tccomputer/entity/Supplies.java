package lk.ijse.tccomputer.entity;

import java.sql.Date;

public class Supplies implements SuperEntity{
    private String SuppliesCode;
    private Date date;
    private String supplierID;

    @Override
    public String toString() {
        return "Supplies{" +
                "SuppliesCode='" + SuppliesCode + '\'' +
                ", date=" + date +
                ", supplierID='" + supplierID + '\'' +
                '}';
    }

    public String getSuppliesCode() {
        return SuppliesCode;
    }

    public void setSuppliesCode(String suppliesCode) {
        SuppliesCode = suppliesCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Supplies(String suppliesCode, Date date, String supplierID) {
        SuppliesCode = suppliesCode;
        this.date = date;
        this.supplierID = supplierID;
    }

    public Supplies() {
    }
}
