package com.rms.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rms.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class FileManager {
    private static final Gson gson = new Gson();

    public static void saveSalesReport(SalesReport report, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(report, writer);
        }
    }

    public static void saveUsers(Map<String, User> users, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
        }
    }

    public static Map<String, User> loadUsers(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, User>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }

    public static void saveMenuItems(Map<String, MenuItem> menuItems, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(menuItems, writer);
        }
    }

    public static Map<String, MenuItem> loadMenuItems(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, MenuItem>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }

    public static void saveOrders(Map<String, Order> orders, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(orders, writer);
        }
    }

    public static Map<String, Order> loadOrders(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, Order>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }

    public static void saveTables(Map<Integer, Table> tables, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(tables, writer);
        }
    }

    public static Map<Integer, Table> loadTables(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<Integer, Table>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }

    public static void saveInventory(Map<String, Ingredient> inventory, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(inventory, writer);
        }
    }

    public static Map<String, Ingredient> loadInventory(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, Ingredient>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }
}
