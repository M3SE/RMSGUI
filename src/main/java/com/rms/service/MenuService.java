package com.rms.service;

import com.rms.model.MenuItem;
import com.rms.util.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuService {
    private Map<String, MenuItem> menuItems;
    private final String filePath = "data/menuItems.json";

    public MenuService() {
        try {
            menuItems = FileManager.loadMenuItems(filePath);
            if (menuItems == null) {
                menuItems = new HashMap<>();
            }
        } catch (IOException e) {
            menuItems = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void addMenuItem(MenuItem item) {
        menuItems.put(item.getId(), item);
        saveMenuItems();
    }

    public boolean removeMenuItem(String itemId) {
        boolean removed = menuItems.remove(itemId) != null;
        saveMenuItems();
        return removed;
    }

    public boolean updateMenuItem(MenuItem item) {
        if (menuItems.containsKey(item.getId())) {
            menuItems.put(item.getId(), item);
            saveMenuItems();
            return true;
        }
        return false;
    }

    public MenuItem getMenuItemById(String itemId) {
        return menuItems.get(itemId);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItems.values().stream().collect(Collectors.toList());
    }

    private void saveMenuItems() {
        try {
            FileManager.saveMenuItems(menuItems, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
