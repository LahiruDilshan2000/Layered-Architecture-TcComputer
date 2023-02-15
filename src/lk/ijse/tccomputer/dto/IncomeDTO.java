package lk.ijse.tccomputer.dto;

public class IncomeDTO {
    private String incomeID;
    private int day;
    private int month;
    private int year;
    private double total;

    public IncomeDTO() {
    }

    public IncomeDTO(String incomeID, int day, int month, int year, double total) {
        this.incomeID = incomeID;
        this.day = day;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    public String getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(String incomeID) {
        this.incomeID = incomeID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "IncomeDTO{" +
                "incomeID='" + incomeID + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", total=" + total +
                '}';
    }
}
