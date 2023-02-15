package lk.ijse.tccomputer.dto;


import java.sql.Date;
import java.util.List;

public class SuppliesDTO {
    private String SuppliesCode;
    private Date date;
    private String supplierID;
    private List<SuppliesDetailDTO> suppliesDetailDTOList;
    private OutComeDTO outComeDTO;

    public SuppliesDTO() {
    }

    public SuppliesDTO(String suppliesCode, Date date, String supplierID) {
        SuppliesCode = suppliesCode;
        this.date = date;
        this.supplierID = supplierID;
    }

    public SuppliesDTO(String suppliesCode, Date date, String supplierID, List<SuppliesDetailDTO> suppliesDetailDTOList, OutComeDTO outComeDTO) {
        SuppliesCode = suppliesCode;
        this.date = date;
        this.supplierID = supplierID;
        this.suppliesDetailDTOList = suppliesDetailDTOList;
        this.outComeDTO = outComeDTO;
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

    public List<SuppliesDetailDTO> getSuppliesDetailDTOList() {
        return suppliesDetailDTOList;
    }

    public void setSuppliesDetailDTOList(List<SuppliesDetailDTO> suppliesDetailDTOList) {
        this.suppliesDetailDTOList = suppliesDetailDTOList;
    }

    public OutComeDTO getOutComeDTO() {
        return outComeDTO;
    }

    public void setOutComeDTO(OutComeDTO outComeDTO) {
        this.outComeDTO = outComeDTO;
    }

    @Override
    public String toString() {
        return "SuppliesDTO{" +
                "SuppliesCode='" + SuppliesCode + '\'' +
                ", date=" + date +
                ", supplierID='" + supplierID + '\'' +
                ", suppliesDetailDTOList=" + suppliesDetailDTOList +
                ", outComeDTO=" + outComeDTO +
                '}';
    }
}
