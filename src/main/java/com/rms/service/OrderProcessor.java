package com.rms.service;

import com.rms.enums.OrderStatus;
import com.rms.model.Ingredient;
import com.rms.model.MenuItem;
import com.rms.model.Order;

import java.util.Map;

public class OrderProcessor {
    private final InventoryService inventoryService;
    private final OrderService orderService;

    public OrderProcessor(InventoryService inventoryService, OrderService orderService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    /**
     * Processes an order by deducting the necessary ingredients from the inventory
     * and updating the order status to 'PREPARING'.
     *
     * @param order The order to process.
     * @return true if the order was successfully processed, false if there was insufficient stock.
     */
    public boolean processOrder(Order order) {
        // Extract required ingredients and quantities from the order
        Map<Ingredient, Integer> ingredientsNeeded = extractIngredients(order);

        // Attempt to use the ingredients from the inventory
        if (inventoryService.useIngredients(ingredientsNeeded)) {
            // If ingredients were successfully used, update the order status
            order.setStatus(OrderStatus.PREPARING);
            orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PREPARING);
            return true;
        } else {
            // If insufficient ingredients, order processing fails
            System.out.println("Insufficient ingredients to process the order.");
            return false;
        }
    }

    /**
     * Marks an order as ready after it has been prepared.
     *
     * @param orderId The ID of the order to mark as ready.
     */
    public void markOrderAsReady(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.READY);
            orderService.updateOrderStatus(orderId, OrderStatus.READY);
            System.out.println("Order " + orderId + " is now READY.");
        } else {
            System.out.println("Order not found.");
        }
    }

    /**
     * Extracts the ingredients and their quantities needed to fulfill an order.
     *
     * @param order The order for which to extract ingredients.
     * @return A map of ingredients and their required quantities.
     */
    private Map<Ingredient, Integer> extractIngredients(Order order) {
        Map<Ingredient, Integer> ingredientsNeeded = new HashMap<>();
        for (Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();
            for (Ingredient ingredient : menuItem.getIngredients()) {
                ingredientsNeeded.merge(ingredient, quantity, Integer::sum);
            }
        }
        return ingredientsNeeded;
    }
}
