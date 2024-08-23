package com.rms.model;

import java.time.LocalDateTime;
import java.util.Map;

public class SalesReport {
    private LocalDateTime reportDate;
    private double totalRevenue;
    private Map<String, Integer> itemSales; // Map of MenuItem ID to quantity sold
    private int totalOrders;

    public SalesReport(LocalDateTime reportDate, double totalRevenue, Map<String, Integer> itemSales, int totalOrders) {
        this.reportDate = reportDate;
        this.totalRevenue = totalRevenue;
        this.itemSales = itemSales;
        this.totalOrders = totalOrders;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public Map<String, Integer> getItemSales() {
        return itemSales;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "reportDate=" + reportDate +
                ", totalRevenue=" + totalRevenue +
                ", itemSales=" + itemSales +
                ", totalOrders=" + totalOrders +
                '}';
    }
}
