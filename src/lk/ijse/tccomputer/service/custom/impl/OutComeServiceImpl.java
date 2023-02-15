package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.OutComeDAO;
import lk.ijse.tccomputer.dto.OutComeDTO;
import lk.ijse.tccomputer.service.custom.OutComeService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.util.List;
import java.util.stream.Collectors;

public class OutComeServiceImpl implements OutComeService {

    private final OutComeDAO outComeDAO;
    private final Convertor convertor;

    public OutComeServiceImpl(){
        outComeDAO= DaoFactory.getInstance().getDAO(DaoType.OUTCOME);
        convertor=new Convertor();
    }

    @Override
    public List<OutComeDTO> getYear() {

        return outComeDAO.getYears().stream().map(outCome -> convertor.fromOutCome(outCome)).collect(Collectors.toList());
    }

    @Override
    public List<OutComeDTO> getMonthlyOutCome(int year, int month) {

        return outComeDAO.getMonthlyOutCome(year, month).stream().map(outCome -> convertor.fromOutCome(outCome)).collect(Collectors.toList());
    }

    @Override
    public double getMonthlyTotal(int year, int month) {

        List<OutComeDTO> outComeDTOList = outComeDAO.getMonthlyOutCome(year, month).stream().map(outCome -> convertor.fromOutCome(outCome)).collect(Collectors.toList());
        double total=0;
        for (OutComeDTO outComeDTO: outComeDTOList){
            total+=outComeDTO.getTotal();
        }
        return total;
    }
}
