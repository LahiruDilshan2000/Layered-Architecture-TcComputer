package lk.ijse.tccomputer.dto;

public class EmployeeDTO {
    private String empID;
    private String name;
    private String mail;
    private String contact;
    private double salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String empID, String name, String mail, String contact) {
        this.empID = empID;
        this.name = name;
        this.mail = mail;
        this.contact = contact;
    }

    public EmployeeDTO(String empID, String name, String mail, String contact, double salary) {
        this.empID = empID;
        this.name = name;
        this.mail = mail;
        this.contact = contact;
        this.salary = salary;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "empID='" + empID + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                '}';
    }
}
