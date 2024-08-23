package com.rms.gui;

import com.rms.model.MenuItem;
import com.rms.model.Order;
import com.rms.service.MenuService;
import com.rms.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderingPanel extends JPanel {
    private final MenuService menuService;
    private final OrderService orderService;
    private final JTable menuTable;
    private final DefaultTableModel tableModel;

    public CustomerOrderingPanel(OrderService orderService, MenuService menuService) {
        this.menuService = new MenuService();
        this.orderService = new OrderService();
        setLayout(new BorderLayout());

        // Setup menu table
        String[] columnNames = {"Item Name", "Description", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton placeOrderButton = new JButton("Place Order");

        buttonPanel.add(placeOrderButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load menu items into the table
        loadMenuItems();

        // Button action
        placeOrderButton.addActionListener(e -> placeOrder());
    }

    private void loadMenuItems() {
        java.util.List<MenuItem> menuItems = menuService.getAllMenuItems();
        for (MenuItem item : menuItems) {
            tableModel.addRow(new Object[]{
                    item.getName(),
                    item.getDescription(),
                    item.getPrice()
            });
        }
    }

    private void placeOrder() {
        int[] selectedRows = menuTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select items to order.");
            return;
        }

        Map<MenuItem, Integer> itemsOrdered = new HashMap<>();
        for (int row : selectedRows) {
            String itemName = (String) tableModel.getValueAt(row, 0);
            MenuItem menuItem = menuService.getAllMenuItems().stream()
                    .filter(item -> item.getName().equals(itemName))
                    .findFirst()
                    .orElse(null);

            if (menuItem != null) {
                itemsOrdered.put(menuItem, 1);  // Default quantity to 1 for simplicity
            }
        }

        if (!itemsOrdered.isEmpty()) {
            Order newOrder = new Order(itemsOrdered, null);  // Assuming no table assignment for customer ordering
            orderService.createOrder(newOrder);
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
        }
    }
}
