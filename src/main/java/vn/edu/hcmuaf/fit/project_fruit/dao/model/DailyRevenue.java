package vn.edu.hcmuaf.fit.project_fruit.dao.model;

import java.util.Date;

public class DailyRevenue {
    private Date day;           // Ngày
    private double totalRevenue; // Tổng doanh thu trong ngày

    // Constructor
    public DailyRevenue(Date day, double totalRevenue) {
        this.day = day;
        this.totalRevenue = totalRevenue;
    }

    // Getter và Setter
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
