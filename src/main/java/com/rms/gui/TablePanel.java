package com.rms.gui;

import com.rms.enums.TableStatus;
import com.rms.model.Table;
import com.rms.service.TableService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private final TableService tableService;
    private final JTable tableTable;
    private final DefaultTableModel tableModel;

    public TablePanel() {
        this.tableService = new TableService();
        setLayout(new BorderLayout());

        // Setup table for table management
        String[] columnNames = {"Table ID", "Size", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton markAvailableButton = new JButton("Mark as Available");
        JButton markReservedButton = new JButton("Mark as Reserved");
        JButton markOccupiedButton = new JButton("Mark as Occupied");

        buttonPanel.add(markAvailableButton);
        buttonPanel.add(markReservedButton);
        buttonPanel.add(markOccupiedButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadTables();

        // Button actions
        markAvailableButton.addActionListener(e -> updateTableStatus(TableStatus.AVAILABLE));
        markReservedButton.addActionListener(e -> updateTableStatus(TableStatus.RESERVED));
        markOccupiedButton.addActionListener(e -> updateTableStatus(TableStatus.OCCUPIED));
    }

    private void loadTables() {
        List<Table> tables = tableService.getAllTables();
        for (Table table : tables) {
            tableModel.addRow(new Object[]{
                    table.getTableId(),
                    table.getSize(),
                    table.getStatus()
            });
        }
    }

    private void updateTableStatus(TableStatus status) {
        int selectedRow = tableTable.getSelectedRow();
        if (selectedRow != -1) {
            int tableId = (int) tableModel.getValueAt(selectedRow, 0);
            tableService.updateTableStatus(tableId, status);
            tableModel.setValueAt(status, selectedRow, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table to update its status.");
        }
    }
}
