package com.rms.gui;

import com.rms.model.MenuItem;
import com.rms.service.MenuService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuManagementPanel extends JPanel {
    private MenuService menuService;
    private JTable menuTable;
    private DefaultTableModel tableModel;

    public MenuManagementPanel(MenuService menuService) {
        this.menuService = new MenuService();
        setLayout(new BorderLayout());

        // Setup table
        String[] columnNames = {"ID", "Name", "Description", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadMenuItems();

        // Button actions (to be implemented)
        // addButton.addActionListener(...);
        // editButton.addActionListener(...);
        // deleteButton.addActionListener(...);
    }

    private void loadMenuItems() {
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        for (MenuItem item : menuItems) {
            tableModel.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice()
            });
        }
    }

    // You can implement the actions for adding, editing, and deleting items here
}
