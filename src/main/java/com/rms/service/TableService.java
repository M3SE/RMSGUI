package com.rms.service;

import com.rms.enums.TableStatus;
import com.rms.model.Order;
import com.rms.model.Table;
import com.rms.util.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableService {
    private Map<Integer, Table> tables;
    private final String filePath = "data/tables.json";

    public TableService() {
        try {
            tables = FileManager.loadTables(filePath);
            if (tables == null) {
                tables = new HashMap<>();
            }
        } catch (IOException e) {
            tables = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void addTable(Table table) {
        tables.put(table.getTableId(), table);
        saveTables();
    }

    public boolean updateTableStatus(int tableId, TableStatus status) {
        Table table = tables.get(tableId);
        if (table != null) {
            table.setStatus(status);
            saveTables();
            return true;
        }
        return false;
    }

    public boolean assignTable(int tableId, Order order) {
        Table table = tables.get(tableId);
        if (table != null && table.getStatus() == TableStatus.AVAILABLE) {
            table.setStatus(TableStatus.OCCUPIED);
            order.setTable(table);
            saveTables();
            return true;
        }
        return false;
    }

    public Table getTableById(int tableId) {
        return tables.get(tableId);
    }

    public List<Table> getAllTables() {
        return tables.values().stream().collect(Collectors.toList());
    }

    private void saveTables() {
        try {
            FileManager.saveTables(tables, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
