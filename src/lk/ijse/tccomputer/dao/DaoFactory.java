package lk.ijse.tccomputer.dao;

import lk.ijse.tccomputer.dao.custom.impl.*;


public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public static DaoFactory getInstance(){

        return daoFactory==null ? (daoFactory=new DaoFactory()) : daoFactory;
    }

    public <T extends SuperDAO>T getDAO(DaoType daoType){

        switch (daoType){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case EMPSALARYDETAIL:
                return (T) new EmpSalaryDetailDAOImpl();
            case INCOME:
                return (T) new IncomeDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDERDETAIL:
                return (T) new OrderDetailDAOImpl();
            case ORDERS:
                return (T) new OrdersDAOImpl();
            case OUTCOME:
                return (T) new OutComeDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            case REPAIR:
                return (T) new RepairDAOImpl();
            case REDUCESITEMDETAIL:
                return (T) new RepairReducesItemDAOImpl();
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            case SUPPLIES:
                return (T) new SuppliesDAOImpl();
            case SUPPLIESDETAIL:
                return (T) new SuppliesDetailDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            default:
                return null;
        }
    }
}
