package com.rms.model;

import com.rms.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Order {
    private String orderId;
    private Map<MenuItem, Integer> items;
    private double totalPrice;
    private OrderStatus status;
    private Table table;
    private LocalDateTime orderTime;

    public Order(Map<MenuItem, Integer> items, Table table) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.totalPrice = calculateTotalPrice();
        this.status = OrderStatus.WAITING;
        this.table = table;
        this.orderTime = LocalDateTime.now();
    }

    private double calculateTotalPrice() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
}
