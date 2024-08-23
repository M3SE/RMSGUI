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

public class TakeoutPanel extends JPanel {
    private final MenuService menuService;
    private final OrderService orderService;
    private final JTable menuTable;
    private final DefaultTableModel tableModel;
    private final JTextField customerNameField;
    private final JTextField phoneNumberField;

    public TakeoutPanel() {
        this.menuService = new MenuService();
        this.orderService = new OrderService();
        setLayout(new BorderLayout());

        // Customer details input
        JPanel detailsPanel = new JPanel(new GridLayout(2, 2));
        detailsPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        detailsPanel.add(customerNameField);
        detailsPanel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        detailsPanel.add(phoneNumberField);

        add(detailsPanel, BorderLayout.NORTH);

        // Setup menu table
        String[] columnNames = {"Item Name", "Description", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton placeOrderButton = new JButton("Place Takeout Order");

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
        String customerName = customerNameField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (customerName.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer details.");
            return;
        }

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
            Order newOrder = new Order(itemsOrdered, null);  // Assuming no table assignment for takeout orders
            orderService.createOrder(newOrder);
            JOptionPane.showMessageDialog(this, "Takeout order placed successfully!");
        }
    }
}
