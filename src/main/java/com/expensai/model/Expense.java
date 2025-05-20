package com.expensai.model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private double amount;
    private LocalDate date;
    private Category category;
    private String description;
    private String receiptPath;
    
    // Constructors
    public Expense() {}
    
    public Expense(double amount, LocalDate date, Category category, String description, String receiptPath) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.receiptPath = receiptPath;
    }
    
    public Expense(int id, double amount, LocalDate date, Category category, String description, String receiptPath) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.receiptPath = receiptPath;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getReceiptPath() {
        return receiptPath;
    }
    
    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }
    
    @Override
    public String toString() {
        return String.format("%s - ₹%.2f - %s", date, amount, category.getName());
    }
}