package lk.ijse.tccomputer.dto;

public class OrderDetailDTO {
    private String orderID;
    private ItemDTO itemDTO;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderID, ItemDTO itemDTO) {
        this.orderID = orderID;
        this.itemDTO = itemDTO;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderID='" + orderID + '\'' +
                ", itemDTO=" + itemDTO +
                '}';
    }
}
