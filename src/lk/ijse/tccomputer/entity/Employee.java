package lk.ijse.tccomputer.entity;

public class Employee implements SuperEntity{
    private String empID;
    private String name;
    private String mail;
    private String contact;

    @Override
    public String toString() {
        return "Employee{" +
                "empID='" + empID + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Employee(String empID, String name, String mail, String contact) {
        this.empID = empID;
        this.name = name;
        this.mail = mail;
        this.contact = contact;
    }

    public Employee() {
    }
}
