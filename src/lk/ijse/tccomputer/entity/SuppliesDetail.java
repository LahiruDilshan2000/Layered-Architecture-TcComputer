package lk.ijse.tccomputer.entity;

public class SuppliesDetail implements SuperEntity{
    private String suppliesCode;
    private String itemCode;
    private int qty;
    private double unitPrice;

    @Override
    public String toString() {
        return "SuppliesDetail{" +
                "suppliesCode='" + suppliesCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getSuppliesCode() {
        return suppliesCode;
    }

    public void setSuppliesCode(String suppliesCode) {
        this.suppliesCode = suppliesCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public SuppliesDetail(String suppliesCode, String itemCode, int qty, double unitPrice) {
        this.suppliesCode = suppliesCode;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public SuppliesDetail() {
    }
}
