package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.CustomerDTO;
import lk.ijse.tccomputer.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(String pk);

    String getNextCustomerId();

    CustomerDTO getCustomerByPk(String pk);

    List<CustomerDTO> findAllCustomer();

    List<CustomerDTO> searchCustomerByText(String text);

    long getCustomerCount();
}
