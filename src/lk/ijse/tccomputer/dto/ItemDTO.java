package lk.ijse.tccomputer.dto;

public class ItemDTO {
    private String itemCode;
    private String brand;
    private String name;
    private int qtyOnHand;
    private double unitPrice;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, int qtyOnHand, double unitPrice) {
        this.itemCode = itemCode;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public ItemDTO(String itemCode, String brand, String name, int qtyOnHand, double unitPrice) {
        this.itemCode = itemCode;
        this.brand = brand;
        this.name = name;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
