package lk.ijse.tccomputer.dto;

public class SuppliesDetailDTO {
    private String suppliesCode;
    private ItemDTO itemDTO;

    public SuppliesDetailDTO() {
    }

    public SuppliesDetailDTO(String suppliesCode, ItemDTO itemDTO) {
        this.suppliesCode = suppliesCode;
        this.itemDTO = itemDTO;
    }

    public String getSuppliesCode() {
        return suppliesCode;
    }

    public void setSuppliesCode(String suppliesCode) {
        this.suppliesCode = suppliesCode;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    @Override
    public String toString() {
        return "SuppliesDetailDTO{" +
                "suppliesCode='" + suppliesCode + '\'' +
                ", itemDTO=" + itemDTO +
                '}';
    }
}
