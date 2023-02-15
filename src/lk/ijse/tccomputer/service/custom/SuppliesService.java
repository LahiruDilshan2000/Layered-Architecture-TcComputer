package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.SuppliesDTO;
import lk.ijse.tccomputer.service.SuperService;

public interface SuppliesService extends SuperService {

    SuppliesDTO saveSupplies(SuppliesDTO suppliesDTO);

    String getNextSuppliesCode();
}
