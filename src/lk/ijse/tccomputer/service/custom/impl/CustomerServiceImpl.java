package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.CustomerDAO;
import lk.ijse.tccomputer.dto.CustomerDTO;
import lk.ijse.tccomputer.service.custom.CustomerService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final Convertor convertor;

    public CustomerServiceImpl() {
        customerDAO = DaoFactory.getInstance().getDAO(DaoType.CUSTOMER);
        convertor = new Convertor();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        customerDAO.save(convertor.toCustomer(customerDTO));
        return customerDTO;

    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

        customerDAO.update(convertor.toCustomer(customerDTO));
        return customerDTO;

    }

    @Override
    public void deleteCustomer(String pk) {

        customerDAO.delete(pk);
    }

    @Override
    public String getNextCustomerId() {

        Optional<String> nextPk = customerDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;

    }

    @Override
    public CustomerDTO getCustomerByPk(String pk) {

       return customerDAO.findAll().stream().filter(customer -> customer.getId().equalsIgnoreCase(pk)).map(customer -> convertor.fromCustomer(customer)).findAny().get();

    }

    @Override
    public List<CustomerDTO> findAllCustomer() {

        return customerDAO.findAll().stream().map(customer -> convertor.fromCustomer(customer)).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomerByText(String text) {

        return customerDAO.searchByText(text).stream().map(customer -> convertor.fromCustomer(customer)).collect(Collectors.toList());
    }

    @Override
    public long getCustomerCount() {

        List<CustomerDTO> tmList = customerDAO.findAll().stream().map(customer -> convertor.fromCustomer(customer)).collect(Collectors.toList());
        return tmList.size();
    }
}
