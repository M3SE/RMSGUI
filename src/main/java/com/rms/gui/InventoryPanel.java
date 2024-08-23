package com.rms.gui;

import com.rms.model.Ingredient;
import com.rms.service.InventoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {
    private final InventoryService inventoryService;
    private final JTable inventoryTable;
    private final DefaultTableModel tableModel;

    public InventoryPanel() {
        this.inventoryService = new InventoryService();
        setLayout(new BorderLayout());

        // Setup table for inventory management
        String[] columnNames = {"Ingredient", "Quantity", "Low Stock Threshold"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton restockButton = new JButton("Restock");
        JButton lowStockAlertButton = new JButton("Low Stock Alerts");

        buttonPanel.add(restockButton);
        buttonPanel.add(lowStockAlertButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadInventory();

        // Button actions
        restockButton.addActionListener(e -> restockIngredient());
        lowStockAlertButton.addActionListener(e -> showLowStockAlerts());
    }

    private void loadInventory() {
        List<Ingredient> ingredients = inventoryService.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            tableModel.addRow(new Object[]{
                    ingredient.getName(),
                    ingredient.getQuantity(),
                    ingredient.getLowStockThreshold()
            });
        }
    }

    private void restockIngredient() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String ingredientName = (String) tableModel.getValueAt(selectedRow, 0);
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity to add:");
            try {
                int quantity = Integer.parseInt(quantityStr);
                inventoryService.updateIngredientQuantity(ingredientName, quantity);
                tableModel.setValueAt(inventoryService.getAllIngredients().stream()
                        .filter(ingredient -> ingredient.getName().equals(ingredientName))
                        .findFirst().orElseThrow().getQuantity(), selectedRow, 1);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity entered.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an ingredient to restock.");
        }
    }

    private void showLowStockAlerts() {
        List<Ingredient> lowStockIngredients = inventoryService.checkLowStock();
        if (lowStockIngredients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All ingredients are sufficiently stocked.");
        } else {
            StringBuilder alertMessage = new StringBuilder("Low stock on:\n");
            for (Ingredient ingredient : lowStockIngredients) {
                alertMessage.append(ingredient.getName()).append(" - Quantity: ").append(ingredient.getQuantity()).append("\n");
            }
            JOptionPane.showMessageDialog(this, alertMessage.toString());
        }
    }
}
