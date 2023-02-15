package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.IncomeDAO;
import lk.ijse.tccomputer.dao.custom.ItemDAO;
import lk.ijse.tccomputer.dao.custom.RepairDAO;
import lk.ijse.tccomputer.dao.custom.RepairReducesItemDAO;
import lk.ijse.tccomputer.db.DBConnection;
import lk.ijse.tccomputer.dto.RepairDTO;
import lk.ijse.tccomputer.dto.RepairReducesItemDetailDTO;
import lk.ijse.tccomputer.service.custom.RepairService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepairServiceImpl implements RepairService {

    private final Connection connection;
    private final RepairDAO repairDAO;
    private final RepairReducesItemDAO repairReducesItemDAO;
    private final ItemDAO itemDAO;
    private final IncomeDAO incomeDAO;
    private final Convertor convertor;

    public RepairServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        repairDAO = DaoFactory.getInstance().getDAO(DaoType.REPAIR);
        repairReducesItemDAO = DaoFactory.getInstance().getDAO(DaoType.REDUCESITEMDETAIL);
        itemDAO = DaoFactory.getInstance().getDAO(DaoType.ITEM);
        incomeDAO = DaoFactory.getInstance().getDAO(DaoType.INCOME);
        convertor = new Convertor();
    }

    @Override
    public RepairDTO saveRepairWithItem(RepairDTO repairDTO) {

        try {
            connection.setAutoCommit(false);

            if (repairDAO.save(convertor.toRepair(repairDTO)) == null)
                throw new RuntimeException("Failed to save repair !");

            for (RepairReducesItemDetailDTO repairReducesItemDetailDTO : repairDTO.getRepairReducesItemDetailDTOList()) {
                if (repairReducesItemDAO.save(convertor.toRepairReducesItemDetail(repairReducesItemDetailDTO)) == null)
                    throw new RuntimeException("Failed to save repair reduces item detail !");
            }

            for (RepairReducesItemDetailDTO repairReducesItemDetailDTO : repairDTO.getRepairReducesItemDetailDTOList()) {
                if (itemDAO.reducesItemQty(convertor.toItem(repairReducesItemDetailDTO.getItemDTO())) == null)
                    throw new RuntimeException("Failed to reduces item !");
            }

            if (incomeDAO.save(convertor.toIncome(repairDTO.getIncomeDTO())) == null)
                throw new RuntimeException("Failed to save income !");

            connection.commit();

            return repairDTO;

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
    public RepairDTO saveRepairWithOutItem(RepairDTO repairDTO) {

        try {
            connection.setAutoCommit(false);

            if (repairDAO.save(convertor.toRepair(repairDTO)) == null)
                throw new RuntimeException("Failed to save repair !");

            if (incomeDAO.save(convertor.toIncome(repairDTO.getIncomeDTO())) == null)
                throw new RuntimeException("Failed to save income !");

            connection.commit();

            return repairDTO;

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
    public String getNextRepairId() {

        Optional<String> nextPk = repairDAO.getNextPk();
        return nextPk.isPresent() ? nextPk.get() : null;
    }

    @Override
    public long getTotalRepairCount() {

        List<RepairDTO> tmList = repairDAO.findAll().stream().filter(repair -> repair.getDate().equals(Date.valueOf(LocalDate.now()))).map(repair -> convertor.fromRepair(repair)).collect(Collectors.toList());
        return tmList.size();
    }
}
