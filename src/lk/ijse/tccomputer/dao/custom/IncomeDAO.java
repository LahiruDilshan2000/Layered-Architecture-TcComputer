package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;

import lk.ijse.tccomputer.entity.Income;

import java.util.List;

public interface IncomeDAO extends CrudDAO<Income,String > {

    List<Income> getYear();

    List<Income> getMonthlyIncome(int year, int month);
}
