package com.rms.service;

import com.rms.model.Order;
import com.rms.model.SalesReport;
import com.rms.util.FileManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReportService {
    private final OrderService orderService;

    public SalesReportService(OrderService orderService) {
        this.orderService = orderService;
    }

    public SalesReport generateDailyReport() {
        List<Order> allOrders = orderService.getAllOrders();
        double totalRevenue = 0;
        Map<String, Integer> itemSales = new HashMap<>();
        int totalOrders = allOrders.size();

        for (Order order : allOrders) {
            totalRevenue += order.getTotalPrice();
            order.getItems().forEach((menuItem, quantity) -> {
                itemSales.put(menuItem.getName(), itemSales.getOrDefault(menuItem.getName(), 0) + quantity);
            });
        }

        SalesReport report = new SalesReport(LocalDateTime.now(), totalRevenue, itemSales, totalOrders);
        saveReport(report);
        return report;
    }

    private void saveReport(SalesReport report) {
        try {
            FileManager.saveSalesReport(report, "data/salesReport.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
