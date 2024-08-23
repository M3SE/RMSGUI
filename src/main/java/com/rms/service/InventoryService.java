package com.rms.service;

import com.rms.model.Ingredient;
import com.rms.util.FileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryService {
    private Map<String, Ingredient> inventory;
    private final String filePath = "data/inventory.json";

    public InventoryService() {
        try {
            inventory = FileManager.loadInventory(filePath);
            if (inventory == null) {
                inventory = new HashMap<>();
            }
        } catch (IOException e) {
            inventory = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void addIngredient(Ingredient ingredient) {
        inventory.put(ingredient.getName(), ingredient);
        saveInventory();
    }

    public boolean updateIngredientQuantity(String ingredientName, int quantity) {
        Ingredient ingredient = inventory.get(ingredientName);
        if (ingredient != null) {
            ingredient.setQuantity(quantity);
            saveInventory();
            return true;
        }
        return false;
    }

    public List<Ingredient> checkLowStock() {
        return inventory.values().stream()
                .filter(Ingredient::isLowStock)
                .collect(Collectors.toList());
    }

    public boolean useIngredients(Map<Ingredient, Integer> ingredientsUsed) {
        for (Map.Entry<Ingredient, Integer> entry : ingredientsUsed.entrySet()) {
            Ingredient ingredient = inventory.get(entry.getKey().getName());
            if (ingredient == null || ingredient.getQuantity() < entry.getValue()) {
                return false; // Not enough stock
            }
            ingredient.setQuantity(ingredient.getQuantity() - entry.getValue());
        }
        saveInventory();
        return true;
    }

    public List<Ingredient> getAllIngredients() {
        return inventory.values().stream().collect(Collectors.toList());
    }

    private void saveInventory() {
        try {
            FileManager.saveInventory(inventory, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
