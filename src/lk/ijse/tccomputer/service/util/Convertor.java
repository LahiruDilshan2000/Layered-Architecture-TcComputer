package lk.ijse.tccomputer.service.util;

import lk.ijse.tccomputer.dto.*;
import lk.ijse.tccomputer.entity.*;

public class Convertor {

    public CustomerDTO fromCustomer(Customer customer){
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());
    }

    public Customer toCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact());
    }

    public EmployeeDTO fromEmployee(Employee employee){
        return new EmployeeDTO(employee.getEmpID(), employee.getName(), employee.getMail(), employee.getContact());
    }

    public Employee toEmployee(EmployeeDTO employeeDTO){
        return new Employee(employeeDTO.getEmpID(), employeeDTO.getName(), employeeDTO.getMail(), employeeDTO.getContact());
    }

    public EmpSalaryDetailDTO fromEmpSalaryDetail(EmpSalaryDetail empSalaryDetail){
        return new EmpSalaryDetailDTO(empSalaryDetail.getEmpID(), empSalaryDetail.getSalary());
    }

    public EmpSalaryDetail toEmpSalaryDetail(EmployeeDTO employeeDTO){
        return new EmpSalaryDetail(employeeDTO.getEmpID(), employeeDTO.getSalary());
    }

    public IncomeDTO fromIncome(Income income){
        return new IncomeDTO(income.getIncomeID(), income.getDay(), income.getMonth(), income.getYear(), income.getTotal());
    }

    public Income toIncome(IncomeDTO incomeDTO){
        return new Income(incomeDTO.getIncomeID(), incomeDTO.getDay(), incomeDTO.getMonth(), incomeDTO.getYear(), incomeDTO.getTotal());
    }

    public ItemDTO fromItem(Item item){
        return new ItemDTO(item.getItemCode(), item.getBrand(), item.getName(), item.getQtyOnHand(), item.getUnitPrice());
    }

    public Item toItem(ItemDTO itemDTO){
        return new Item(itemDTO.getItemCode(), itemDTO.getBrand(), itemDTO.getName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
    }

    public OrdersDTO fromOrder(Orders orders){
        return new OrdersDTO(orders.getOrderID(), orders.getDate(), orders.getCustomerID());
    }

    public Orders toOrders(OrdersDTO ordersDTO){
        return new Orders(ordersDTO.getOrderID(), ordersDTO.getDate(), ordersDTO.getCustomerID());
    }

    public OrderDetailDTO fromOrderDetail(OrderDetail orderDetail){
        return new OrderDetailDTO(orderDetail.getOrderID(), new ItemDTO(orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice()));
    }

    public OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO){
        return new OrderDetail(orderDetailDTO.getOrderID(), orderDetailDTO.getItemDTO().getItemCode(), orderDetailDTO.getItemDTO().getQtyOnHand(), orderDetailDTO.getItemDTO().getUnitPrice());
    }

    public OutComeDTO fromOutCome(OutCome outCome){
        return new OutComeDTO(outCome.getOutcomeID(), outCome.getDay(), outCome.getMonth(), outCome.getYear(), outCome.getTotal());
    }

    public OutCome toOutCome(OutComeDTO outComeDTO){
        return new OutCome(outComeDTO.getOutcomeID(), outComeDTO.getDay(), outComeDTO.getMonth(), outComeDTO.getYear(), outComeDTO.getTotal());
    }

    public RepairDTO fromRepair(Repair repair){
        return new RepairDTO(repair.getRepairID(), repair.getItemType(), repair.getItemName(), repair.getDate(), repair.getAmount(), repair.getCustomerID());
    }

    public Repair toRepair(RepairDTO repairDTO){
        return new Repair(repairDTO.getRepairID(), repairDTO.getItemType(), repairDTO.getItemName(), repairDTO.getDate(), repairDTO.getAmount(),repairDTO.getCustomerID());
    }

    public RepairReducesItemDetailDTO fromRepairReducesItemDetail(RepairReducesItemDetail repairReducesItemDetail){
        return new RepairReducesItemDetailDTO(repairReducesItemDetail.getRepairID(), new ItemDTO(repairReducesItemDetail.getItemCode(), repairReducesItemDetail.getQty(), repairReducesItemDetail.getUnitPrice()));
    }

    public RepairReducesItemDetail toRepairReducesItemDetail(RepairReducesItemDetailDTO repairReducesItemDetailDTO){
        return new RepairReducesItemDetail(repairReducesItemDetailDTO.getRepairID(), repairReducesItemDetailDTO.getItemDTO().getItemCode(), repairReducesItemDetailDTO.getItemDTO().getQtyOnHand(), repairReducesItemDetailDTO.getItemDTO().getUnitPrice());
    }

    public SupplierDTO fromSupplier(Supplier supplier){
        return new SupplierDTO(supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getContact());
    }

    public Supplier toSupplier(SupplierDTO supplierDTO){
        return new Supplier(supplierDTO.getId(), supplierDTO.getName(), supplierDTO.getAddress(), supplierDTO.getContact());
    }

    public SuppliesDTO fromSupplies(Supplies supplies){
        return new SuppliesDTO(supplies.getSuppliesCode(), supplies.getDate(), supplies.getSupplierID());
    }

    public Supplies toSupplies(SuppliesDTO suppliesDTO){
        return new Supplies(suppliesDTO.getSuppliesCode(), suppliesDTO.getDate(), suppliesDTO.getSupplierID());
    }

    public SuppliesDetailDTO fromSuppliesDetail(SuppliesDetail suppliesDetail){
        return new SuppliesDetailDTO(suppliesDetail.getSuppliesCode(), new ItemDTO(suppliesDetail.getItemCode(), suppliesDetail.getQty(), suppliesDetail.getUnitPrice()));
    }

    public SuppliesDetail toSuppliesDetail(SuppliesDetailDTO suppliesDetailDTO){
        return new SuppliesDetail(suppliesDetailDTO.getSuppliesCode(), suppliesDetailDTO.getItemDTO().getItemCode(), suppliesDetailDTO.getItemDTO().getQtyOnHand(), suppliesDetailDTO.getItemDTO().getUnitPrice());
    }

    public UserDTO fromUser(User user){
        return new UserDTO(user.getUserID(), user.getPassword(), user.getRole());
    }

    public User toUser(UserDTO userDTO){
        return new User(userDTO.getUserID(), userDTO.getPassword(), userDTO.getRole());
    }
}