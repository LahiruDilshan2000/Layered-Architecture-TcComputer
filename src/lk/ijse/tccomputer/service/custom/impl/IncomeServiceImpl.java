package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.IncomeDAO;
import lk.ijse.tccomputer.dto.IncomeDTO;
import lk.ijse.tccomputer.service.custom.IncomeService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeServiceImpl implements IncomeService {

    private final IncomeDAO incomeDAO;
    private final Convertor convertor;

    public IncomeServiceImpl(){
        incomeDAO= DaoFactory.getInstance().getDAO(DaoType.INCOME);
        convertor=new Convertor();
    }

    @Override
    public List<IncomeDTO> getYear() {

        return incomeDAO.getYear().stream().map(income -> convertor.fromIncome(income)).collect(Collectors.toList());

    }

    @Override
    public List<IncomeDTO> getMonthlyIncome(int year, int month) {

        return incomeDAO.getMonthlyIncome(year, month).stream().map(income -> convertor.fromIncome(income)).collect(Collectors.toList());
    }

    @Override
    public List<IncomeDTO> getMonthlyIncomeWithoutGroup() {

        return incomeDAO.findAll().stream().filter(income -> income.getYear()==LocalDate.now().getYear() && income.getMonth()==LocalDate.now().getMonthValue()).map(income -> convertor.fromIncome(income)).collect(Collectors.toList());
    }

    @Override
    public double getMonthlyTotal(int year, int month) {

        List<IncomeDTO> incomeDTOList = incomeDAO.getMonthlyIncome(year, month).stream().map(income -> convertor.fromIncome(income)).collect(Collectors.toList());
        double total=0;
        for (IncomeDTO incomeDTO: incomeDTOList){
            total+=incomeDTO.getTotal();
        }
        return total;
    }

    @Override
    public double getMonthlyTotalSeparately(String pk) {

        List<IncomeDTO> tmList = incomeDAO.findAll().stream().filter(income -> income.getIncomeID().startsWith(pk) && income.getYear()== LocalDate.now().getYear() && income.getMonth()==LocalDate.now().getMonthValue() && income.getDay()==LocalDate.now().getDayOfMonth()).map(income -> convertor.fromIncome(income)).collect(Collectors.toList());
        double total=0;
        for (IncomeDTO incomeDTO: tmList){
            total+=incomeDTO.getTotal();
        }
        return total;
    }
}
