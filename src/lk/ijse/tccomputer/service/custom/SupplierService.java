package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.SupplierDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface SupplierService extends SuperService {

    SupplierDTO saveSupplier(SupplierDTO supplierDTO);

    SupplierDTO updateSupplier(SupplierDTO supplierDTO);

    void deleteSupplier(String pk);

    String getNextSupplierId();

    SupplierDTO getSupplierByPk(String pk);

    List<SupplierDTO> findAllSupplier();

    List<SupplierDTO> searchSupplierByText(String text);

    long getSupplierCount();

}
