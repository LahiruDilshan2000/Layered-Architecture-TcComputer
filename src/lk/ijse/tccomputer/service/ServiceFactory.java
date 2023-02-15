package lk.ijse.tccomputer.service;

import lk.ijse.tccomputer.service.custom.impl.*;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstances(){
        return serviceFactory==null ? (serviceFactory=new ServiceFactory()) : serviceFactory;
    }

    public <T extends SuperService>T getService(ServiceType serviceType){
        switch (serviceType){
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case EMPLOYEE:
                return (T) new EmployeeServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case ORDER:
                return (T) new OrdersServiceImpl();
            case REPAIR:
                return (T) new RepairServiceImpl();
            case SUPPLIER:
                return (T) new SupplierServiceImpl();
            case SUPPLIES:
                return (T) new SuppliesServiceImpl();
            case USER:
                return (T) new UserServiceImpl();
            case INCOME:
                return (T) new IncomeServiceImpl();
            case OUTCOME:
                return (T) new OutComeServiceImpl();
            default:
                return null;
        }
    }
}
