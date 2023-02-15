package lk.ijse.tccomputer.dto;


import java.sql.Date;
import java.util.List;

public class RepairDTO {
    private String repairID;
    private String itemType;
    private String itemName;
    private Date date;
    private double amount;
    private String customerID;
    private List<RepairReducesItemDetailDTO> repairReducesItemDetailDTOList;
    private IncomeDTO incomeDTO;

    public RepairDTO() {
    }

    public RepairDTO(String repairID, String itemType, String itemName, Date date, double amount, String customerID) {
        this.repairID = repairID;
        this.itemType = itemType;
        this.itemName = itemName;
        this.date = date;
        this.amount = amount;
        this.customerID = customerID;
    }

    public RepairDTO(String repairID, String itemType, String itemName, Date date, double amount, String customerID, IncomeDTO incomeDTO) {
        this.repairID = repairID;
        this.itemType = itemType;
        this.itemName = itemName;
        this.date = date;
        this.amount = amount;
        this.customerID = customerID;
        this.incomeDTO = incomeDTO;
    }

    public RepairDTO(String repairID, String itemType, String itemName, Date date, double amount, String customerID, List<RepairReducesItemDetailDTO> repairReducesItemDetailDTOList, IncomeDTO incomeDTO) {
        this.repairID = repairID;
        this.itemType = itemType;
        this.itemName = itemName;
        this.date = date;
        this.amount = amount;
        this.customerID = customerID;
        this.repairReducesItemDetailDTOList = repairReducesItemDetailDTOList;
        this.incomeDTO = incomeDTO;
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

    public List<RepairReducesItemDetailDTO> getRepairReducesItemDetailDTOList() {
        return repairReducesItemDetailDTOList;
    }

    public void setRepairReducesItemDetailDTOList(List<RepairReducesItemDetailDTO> repairReducesItemDetailDTOList) {
        this.repairReducesItemDetailDTOList = repairReducesItemDetailDTOList;
    }

    public IncomeDTO getIncomeDTO() {
        return incomeDTO;
    }

    public void setIncomeDTO(IncomeDTO incomeDTO) {
        this.incomeDTO = incomeDTO;
    }

    @Override
    public String toString() {
        return "RepairDTO{" +
                "repairID='" + repairID + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemName='" + itemName + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", customerID='" + customerID + '\'' +
                ", repairReducesItemDetailDTOList=" + repairReducesItemDetailDTOList +
                ", incomeDTO=" + incomeDTO +
                '}';
    }
}
