package com.rms.gui;

import com.rms.enums.UserRole;
import com.rms.model.User;
import com.rms.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StaffManagementPanel extends JPanel {
    private final UserService userService;
    private final JTable staffTable;
    private final DefaultTableModel tableModel;

    public StaffManagementPanel() {
        this.userService = new UserService();
        setLayout(new BorderLayout());

        // Setup table for staff management
        String[] columnNames = {"Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Staff");
        JButton editButton = new JButton("Edit Role");
        JButton deleteButton = new JButton("Remove Staff");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadStaff();

        // Button actions
        addButton.addActionListener(e -> addStaff());
        editButton.addActionListener(e -> editStaffRole());
        deleteButton.addActionListener(e -> removeStaff());
    }

    private void loadStaff() {
        List<User> staff = userService.getAllUsers(); // Assuming you have this method in UserService
        for (User user : staff) {
            tableModel.addRow(new Object[]{
                    user.getUsername(),
                    user.getRole().getDisplayName()
            });
        }
    }

    private void addStaff() {
        String username = JOptionPane.showInputDialog(this, "Enter new staff username:");
        if (username == null || username.trim().isEmpty()) {
            return;
        }

        String role = JOptionPane.showInputDialog(this, "Enter role (Staff/Manager):");
        if (role == null || (!role.equalsIgnoreCase("Staff") && !role.equalsIgnoreCase("Manager"))) {
            JOptionPane.showMessageDialog(this, "Invalid role entered.");
            return;
        }

        UserRole userRole = role.equalsIgnoreCase("Manager") ? UserRole.MANAGER : UserRole.STAFF;
        String password = JOptionPane.showInputDialog(this, "Enter password for new staff:");

        User newUser = new User(username, password, userRole);
        boolean success = userService.registerUser(newUser);
        if (success) {
            tableModel.addRow(new Object[]{newUser.getUsername(), newUser.getRole().getDisplayName()});
            JOptionPane.showMessageDialog(this, "Staff added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add staff. Username might already exist.");
        }
    }

    private void editStaffRole() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            String newRole = JOptionPane.showInputDialog(this, "Enter new role (Staff/Manager):");

            if (newRole == null || (!newRole.equalsIgnoreCase("Staff") && !newRole.equalsIgnoreCase("Manager"))) {
                JOptionPane.showMessageDialog(this, "Invalid role entered.");
                return;
            }

            UserRole userRole = newRole.equalsIgnoreCase("Manager") ? UserRole.MANAGER : UserRole.STAFF;
            User user = userService.getUserByUsername(username);
            user.setRole(userRole);
            tableModel.setValueAt(user.getRole().getDisplayName(), selectedRow, 1);
            JOptionPane.showMessageDialog(this, "Staff role updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staff member to edit their role.");
        }
    }

    private void removeStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            userService.removeUser(username); // Assuming you have this method in UserService
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Staff removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staff member to remove.");
        }
    }
}
