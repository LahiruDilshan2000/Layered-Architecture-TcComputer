package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.SupplierDAO;
import lk.ijse.tccomputer.dto.SupplierDTO;
import lk.ijse.tccomputer.service.custom.SupplierService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {

    private final SupplierDAO supplierDAO;
    private final Convertor convertor;

    public SupplierServiceImpl(){
        supplierDAO= DaoFactory.getInstance().getDAO(DaoType.SUPPLIER);
        convertor=new Convertor();
    }
    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {

        supplierDAO.save(convertor.toSupplier(supplierDTO));
        return supplierDTO;

    }

    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) {

        supplierDAO.update(convertor.toSupplier(supplierDTO));
        return supplierDTO;

    }

    @Override
    public void deleteSupplier(String pk) {

        supplierDAO.delete(pk);

    }

    @Override
    public String getNextSupplierId() {

        Optional<String> nextPk = supplierDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;
    }

    @Override
    public SupplierDTO getSupplierByPk(String pk) {

        return supplierDAO.findAll().stream().filter(supplier -> supplier.getId().equalsIgnoreCase(pk)).map(supplier -> convertor.fromSupplier(supplier)).findAny().get();
    }

    @Override
    public List<SupplierDTO> findAllSupplier() {

        return supplierDAO.findAll().stream().map(supplier -> convertor.fromSupplier(supplier)).collect(Collectors.toList());

    }

    @Override
    public List<SupplierDTO> searchSupplierByText(String text) {

        return supplierDAO.searchByText(text).stream().map(supplier -> convertor.fromSupplier(supplier)).collect(Collectors.toList());

    }

    @Override
    public long getSupplierCount() {

        List<SupplierDTO> tmList = supplierDAO.findAll().stream().map(supplier -> convertor.fromSupplier(supplier)).collect(Collectors.toList());
        return tmList.size();
    }
}
