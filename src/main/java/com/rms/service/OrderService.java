package com.rms.service;

import com.rms.enums.OrderStatus;
import com.rms.model.Order;
import com.rms.util.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private Map<String, Order> orders;
    private final String filePath = "data/orders.json";

    public OrderService() {
        try {
            orders = FileManager.loadOrders(filePath);
            if (orders == null) {
                orders = new HashMap<>();
            }
        } catch (IOException e) {
            orders = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void createOrder(Order order) {
        orders.put(order.getOrderId(), order);
        saveOrders();
    }

    public boolean updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            saveOrders();
            return true;
        }
        return false;
    }

    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders() {
        return orders.values().stream().collect(Collectors.toList());
    }

    public double calculateTotalRevenue() {
        return orders.values().stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    private void saveOrders() {
        try {
            FileManager.saveOrders(orders, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
