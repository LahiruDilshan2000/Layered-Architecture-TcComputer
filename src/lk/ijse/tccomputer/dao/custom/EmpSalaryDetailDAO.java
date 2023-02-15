package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.EmpSalaryDetail;

public interface EmpSalaryDetailDAO extends CrudDAO<EmpSalaryDetail,String> {

    boolean existByPk(String pk);
}
