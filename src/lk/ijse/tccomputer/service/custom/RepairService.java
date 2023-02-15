package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.RepairDTO;
import lk.ijse.tccomputer.service.SuperService;


public interface RepairService extends SuperService {

    RepairDTO saveRepairWithItem(RepairDTO repairDTO);

    RepairDTO saveRepairWithOutItem(RepairDTO repairDTO);

    String getNextRepairId();

    long getTotalRepairCount();
}
