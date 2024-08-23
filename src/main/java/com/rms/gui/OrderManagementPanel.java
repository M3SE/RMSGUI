public class OrderManagementPanel extends JPanel {
    private final OrderService orderService;
    private final OrderProcessor orderProcessor;
    private final JTable orderTable;
    private final DefaultTableModel tableModel;

    public OrderManagementPanel(OrderService orderService, InventoryService inventoryService) {
        this.orderService = orderService;
        this.orderProcessor = new OrderProcessor(inventoryService, orderService);
        setLayout(new BorderLayout());

        // Setup table
        String[] columnNames = {"Order ID", "Total Price", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton processOrderButton = new JButton("Process Order");
        JButton markAsReadyButton = new JButton("Mark as Ready");

        buttonPanel.add(processOrderButton);
        buttonPanel.add(markAsReadyButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data into the table
        loadOrders();

        // Button actions
        processOrderButton.addActionListener(e -> processSelectedOrder());
        markAsReadyButton.addActionListener(e -> markOrderAsReady());
    }

    private void loadOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getTotalPrice(),
                    order.getStatus()
            });
        }
    }

    private void processSelectedOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderId = (String) tableModel.getValueAt(selectedRow, 0);
            Order order = orderService.getOrderById(orderId);
            if (order != null && order.getStatus() == OrderStatus.WAITING) {
                boolean processed = orderProcessor.processOrder(order);
                if (processed) {
                    tableModel.setValueAt(OrderStatus.PREPARING, selectedRow, 2);
                }
            }
        }
    }

    private void markOrderAsReady() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            String orderId = (String) tableModel.getValueAt(selectedRow, 0);
            orderProcessor.markOrderAsReady(orderId);
            tableModel.setValueAt(OrderStatus.Ready, selectedRow, 2);
        }
    }
}
