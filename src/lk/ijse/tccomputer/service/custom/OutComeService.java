package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.OutComeDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface OutComeService extends SuperService {

    List<OutComeDTO> getYear();

    List<OutComeDTO> getMonthlyOutCome(int year, int month);

    double getMonthlyTotal(int year, int month);
}
