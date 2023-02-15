package lk.ijse.tccomputer.dto;

public class EmpSalaryDetailDTO {
    private String empID;
    private double salary;

    public EmpSalaryDetailDTO() {
    }

    public EmpSalaryDetailDTO(String empID, double salary) {
        this.empID = empID;
        this.salary = salary;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmpSalaryDetailDTO{" +
                "rmpID='" + empID + '\'' +
                ", salary=" + salary +
                '}';
    }
}
