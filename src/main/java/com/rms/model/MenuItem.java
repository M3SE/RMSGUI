package com.rms.model;

import java.util.List;
import java.util.UUID;

public class MenuItem {
    private String id;
    private String name;
    private String description;
    private int preparationTime;
    private double price;
    private List<Ingredient> ingredients;

    public MenuItem(String name, String description, int preparationTime, double price, List<Ingredient> ingredients) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
