package lk.ijse.tccomputer.entity;

public class User implements SuperEntity{
    private String userID;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String userID, String password, String role) {
        this.userID = userID;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
}
