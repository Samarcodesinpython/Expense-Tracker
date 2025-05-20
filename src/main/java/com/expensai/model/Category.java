package com.expensai.model;

import javafx.scene.paint.Color;

public class Category {
    private int id;
    private String name;
    private String color;
    private String icon;
    private double budget;
    private double spent;
    private double remaining;
    
    // Constructors
    public Category() {}
    
    public Category(String name, String color, String icon, double budget) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.budget = budget;
        this.spent = 0;
        this.remaining = budget;
    }
    
    public Category(int id, String name, String color, String icon, double budget) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.budget = budget;
        this.spent = 0;
        this.remaining = budget;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
        updateRemaining();
    }
    
    public double getSpent() {
        return spent;
    }
    
    public void setSpent(double spent) {
        this.spent = spent;
        updateRemaining();
    }
    
    public double getRemaining() {
        return remaining;
    }
    
    private void updateRemaining() {
        this.remaining = this.budget - this.spent;
    }
    
    public Color getColorAsColor() {
        return Color.valueOf(color);
    }
    
    @Override
    public String toString() {
        return name;
    }
}