package lk.ijse.tccomputer.service.custom.impl;

import lk.ijse.tccomputer.dao.DaoFactory;
import lk.ijse.tccomputer.dao.DaoType;
import lk.ijse.tccomputer.dao.custom.UserDAO;
import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.entity.User;
import lk.ijse.tccomputer.service.custom.UserService;
import lk.ijse.tccomputer.service.util.Convertor;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final Convertor convertor;

    public UserServiceImpl(){
        userDAO= DaoFactory.getInstance().getDAO(DaoType.USER);
        convertor=new Convertor();
    }
    @Override
    public boolean isExitsByPk(String pk) {

        return userDAO.existByPk(pk);

    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        userDAO.save(convertor.toUser(userDTO));
        return userDTO;

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        userDAO.update(convertor.toUser(userDTO));
        return userDTO;

    }

    @Override
    public UserDTO getUserDetail(String pk) {

        Optional<User> user = userDAO.getUser(pk);
        return user.isPresent() ? convertor.fromUser(user.get()) : null;

    }
}
