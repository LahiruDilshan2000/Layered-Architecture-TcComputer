package lk.ijse.tccomputer.entity;

public class OutCome implements SuperEntity{
    private String outcomeID;
    private int day;
    private int month;
    private int year;
    private double total;

    @Override
    public String toString() {
        return "OutCome{" +
                "outcomeID='" + outcomeID + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", total=" + total +
                '}';
    }

    public String getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(String outcomeID) {
        this.outcomeID = outcomeID;
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

    public OutCome(String outcomeID, int day, int month, int year, double total) {
        this.outcomeID = outcomeID;
        this.day = day;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    public OutCome() {
    }
}
