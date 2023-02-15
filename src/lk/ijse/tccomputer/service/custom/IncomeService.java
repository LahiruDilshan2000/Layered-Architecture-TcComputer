package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.IncomeDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface IncomeService extends SuperService {

    List<IncomeDTO> getYear();

    List<IncomeDTO> getMonthlyIncome(int year,int month);

    List<IncomeDTO> getMonthlyIncomeWithoutGroup();

    double getMonthlyTotal(int year, int month);

    double getMonthlyTotalSeparately(String pk);
}
