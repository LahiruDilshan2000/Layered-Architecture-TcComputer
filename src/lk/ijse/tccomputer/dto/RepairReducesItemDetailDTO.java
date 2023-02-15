package lk.ijse.tccomputer.dto;

public class RepairReducesItemDetailDTO {
    private String repairID;
    private ItemDTO itemDTO;

    public RepairReducesItemDetailDTO() {
    }

    public RepairReducesItemDetailDTO(String repairID, ItemDTO itemDTO) {
        this.repairID = repairID;
        this.itemDTO = itemDTO;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    @Override
    public String toString() {
        return "RepairReducesItemDetailDTO{" +
                "repairID='" + repairID + '\'' +
                ", itemDTO=" + itemDTO +
                '}';
    }
}
