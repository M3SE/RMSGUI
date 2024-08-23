package com.rms.model;

import com.rms.enums.TableStatus;

public class Table {
    private int tableId;
    private int size;
    private TableStatus status;

    public Table(int tableId, int size) {
        this.tableId = tableId;
        this.size = size;
        this.status = TableStatus.AVAILABLE;
    }

    public int getTableId() {
        return tableId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
