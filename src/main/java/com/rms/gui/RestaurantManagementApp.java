package com.rms.gui;

import com.rms.service.InventoryService;
import com.rms.service.MenuService;
import com.rms.service.OrderService;

import javax.swing.*;
import java.awt.*;

public class RestaurantManagementApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public RestaurantManagementApp() {
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize shared services
        OrderService orderService = new OrderService();
        InventoryService inventoryService = new InventoryService();
        MenuService menuService = new MenuService();

        // Setup CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to the mainPanel, passing in shared services
        mainPanel.add(new MenuManagementPanel(menuService), "MenuManagement");
        mainPanel.add(new OrderManagementPanel(orderService, inventoryService), "OrderManagement");
        mainPanel.add(new TablePanel(orderService), "TableManagement");
        mainPanel.add(new InventoryPanel(inventoryService), "InventoryManagement");
        mainPanel.add(new SalesReportPanel(orderService), "SalesReport");
        mainPanel.add(new StaffManagementPanel(), "StaffManagement");
        mainPanel.add(new CustomerOrderingPanel(orderService, menuService), "CustomerOrdering");
        mainPanel.add(new TakeoutPanel(orderService, menuService), "Takeout");

        // Add mainPanel to the frame
        add(mainPanel);

        // Setup MenuBar
        setJMenuBar(createMenuBar());

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu manageMenu = new JMenu("Manage");
        JMenuItem menuItemMenu = new JMenuItem("Menu Management");
        JMenuItem menuItemOrders = new JMenuItem("Order Management");
        JMenuItem menuItemTables = new JMenuItem("Table Management");
        JMenuItem menuItemInventory = new JMenuItem("Inventory Management");
        JMenuItem menuItemReports = new JMenuItem("Sales Reports");
        JMenuItem menuItemStaff = new JMenuItem("Staff Management");
        JMenuItem menuItemCustomerOrder = new JMenuItem("Customer Ordering");
        JMenuItem menuItemTakeout = new JMenuItem("Takeout Ordering");

        menuItemMenu.addActionListener(e -> cardLayout.show(mainPanel, "MenuManagement"));
        menuItemOrders.addActionListener(e -> cardLayout.show(mainPanel, "OrderManagement"));
        menuItemTables.addActionListener(e -> cardLayout.show(mainPanel, "TableManagement"));
        menuItemInventory.addActionListener(e -> cardLayout.show(mainPanel, "InventoryManagement"));
        menuItemReports.addActionListener(e -> cardLayout.show(mainPanel, "SalesReport"));
        menuItemStaff.addActionListener(e -> cardLayout.show(mainPanel, "StaffManagement"));
        menuItemCustomerOrder.addActionListener(e -> cardLayout.show(mainPanel, "CustomerOrdering"));
        menuItemTakeout.addActionListener(e -> cardLayout.show(mainPanel, "Takeout"));

        manageMenu.add(menuItemMenu);
        manageMenu.add(menuItemOrders);
        manageMenu.add(menuItemTables);
        manageMenu.add(menuItemInventory);
        manageMenu.add(menuItemReports);
        manageMenu.add(menuItemStaff);
        manageMenu.add(menuItemCustomerOrder);
        manageMenu.add(menuItemTakeout);

        menuBar.add(manageMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantManagementApp::new);
    }
}
