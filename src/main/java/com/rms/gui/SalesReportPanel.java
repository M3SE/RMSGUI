package com.rms.gui;

import com.rms.model.SalesReport;
import com.rms.service.SalesReportService;
import com.rms.service.OrderService;

import javax.swing.*;
import java.awt.*;

public class SalesReportPanel extends JPanel {
    private JTextArea reportArea;
    private SalesReportService salesReportService;

    public SalesReportPanel(OrderService orderService) {
        this.salesReportService = new SalesReportService(orderService);  // Pass in the OrderService

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton generateReportButton = new JButton("Generate Report");
        JButton exportReportButton = new JButton("Export Report");

        buttonPanel.add(generateReportButton);
        buttonPanel.add(exportReportButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        generateReportButton.addActionListener(e -> generateReport());
        exportReportButton.addActionListener(e -> exportReport());
    }

    private void generateReport() {
        SalesReport report = salesReportService.generateDailyReport();
        reportArea.setText(report.toString());
    }

    private void exportReport() {
        SalesReport report = salesReportService.generateDailyReport();
        salesReportService.exportReport(report, "sales_report.txt");
        JOptionPane.showMessageDialog(this, "Sales report exported successfully!");
    }
}
