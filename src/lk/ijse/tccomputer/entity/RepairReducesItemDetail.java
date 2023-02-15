package lk.ijse.tccomputer.entity;

public class RepairReducesItemDetail implements SuperEntity{
    private String repairID;
    private String itemCode;
    private int qty;
    private double unitPrice;

    @Override
    public String toString() {
        return "ReducesItemDetail{" +
                "repairID='" + repairID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
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

    public RepairReducesItemDetail(String repairID, String itemCode, int qty, double unitPrice) {
        this.repairID = repairID;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public RepairReducesItemDetail() {
    }
}
