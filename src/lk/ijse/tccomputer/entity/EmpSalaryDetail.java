package lk.ijse.tccomputer.entity;

public class EmpSalaryDetail implements SuperEntity{
    private String empID;
    private double salary;

    public EmpSalaryDetail() {
    }

    public EmpSalaryDetail(String empID, double salary) {
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
        return "EmpSalaryDetail{" +
                "empID='" + empID + '\'' +
                ", salary=" + salary +
                '}';
    }
}
