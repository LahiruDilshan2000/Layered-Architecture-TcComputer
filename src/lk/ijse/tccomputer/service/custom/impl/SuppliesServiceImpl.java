package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.ItemDAO;
import lk.ijse.tccomputer.dao.custom.OutComeDAO;
import lk.ijse.tccomputer.dao.custom.SuppliesDAO;
import lk.ijse.tccomputer.dao.custom.SuppliesDetailDAO;
import lk.ijse.tccomputer.db.DBConnection;
import lk.ijse.tccomputer.dto.SuppliesDTO;
import lk.ijse.tccomputer.dto.SuppliesDetailDTO;
import lk.ijse.tccomputer.service.custom.SuppliesService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class SuppliesServiceImpl implements SuppliesService {

    private final Connection connection;
    private final SuppliesDAO suppliesDAO;
    private final SuppliesDetailDAO suppliesDetailDAO;
    private final ItemDAO itemDAO;
    private final OutComeDAO outComeDAO;
    private final Convertor convertor;

    public SuppliesServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        suppliesDAO = DaoFactory.getInstance().getDAO(DaoType.SUPPLIES);
        suppliesDetailDAO = DaoFactory.getInstance().getDAO(DaoType.SUPPLIESDETAIL);
        itemDAO = DaoFactory.getInstance().getDAO(DaoType.ITEM);
        outComeDAO = DaoFactory.getInstance().getDAO(DaoType.OUTCOME);
        convertor = new Convertor();
    }

    @Override
    public SuppliesDTO saveSupplies(SuppliesDTO suppliesDTO) {

        try {
            connection.setAutoCommit(false);

            if (suppliesDAO.save(convertor.toSupplies(suppliesDTO)) == null)
                throw new RuntimeException("Failed to save supplies !");

            for (SuppliesDetailDTO suppliesDetailDTO : suppliesDTO.getSuppliesDetailDTOList()) {
                if (suppliesDetailDAO.save(convertor.toSuppliesDetail(suppliesDetailDTO)) == null)
                    throw new RuntimeException("Failed to save supplies detail !");
            }

            for (SuppliesDetailDTO suppliesDetailDTO : suppliesDTO.getSuppliesDetailDTOList()) {
                if (itemDAO.updateItemQty(convertor.toItem(suppliesDetailDTO.getItemDTO())) == null)
                    throw new RuntimeException("Failed to update item !");
            }

            if (outComeDAO.save(convertor.toOutCome(suppliesDTO.getOutComeDTO())) == null)
                throw new RuntimeException("Failed to save outcome !");

            connection.commit();
            return suppliesDTO;

        } catch (Throwable t) {
            try {
                connection.rollback();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getNextSuppliesCode() {

        Optional<String> nextPk = suppliesDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;
    }
}
