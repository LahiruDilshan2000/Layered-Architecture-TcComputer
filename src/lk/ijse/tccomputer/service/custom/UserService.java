package lk.ijse.tccomputer.service.custom;

import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.service.SuperService;

public interface UserService extends SuperService {

    boolean isExitsByPk(String pk);

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO getUserDetail(String pk);
}
