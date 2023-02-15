package lk.ijse.tccomputer.util;

import lk.ijse.tccomputer.dto.UserDTO;

public class UserHolder {

    private static UserHolder userHolder;
    private UserDTO userDTO;

    private UserHolder(){

    }

    public static UserHolder getInstance(){
        return userHolder==null ? userHolder=new UserHolder() : userHolder;
    }

    public void setUserDTO(UserDTO userDTO){
        this.userDTO=userDTO;
    }

    public UserDTO getUserDTO(){
        return userDTO;
    }
}
