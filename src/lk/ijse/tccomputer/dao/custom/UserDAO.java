package lk.ijse.tccomputer.dao.custom;

import lk.ijse.tccomputer.dao.CrudDAO;
import lk.ijse.tccomputer.entity.User;

import java.util.Optional;

public interface UserDAO extends CrudDAO<User,String > {

    boolean existByPk(String pk);

    Optional<User> getUser(String userID);
}
