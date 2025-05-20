package com.expensai.model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private int userId;
    private double amount;
    private int categoryId;
    private String description;
    private LocalDate date;
    private String receiptPath;
    private String categoryName; // For display purposes
    private String categoryColor; // For display purposes
    
    // Constructors
    public Expense() {}
    
    public Expense(int userId, double amount, int categoryId, String description, LocalDate date) {
        this.userId = userId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
        this.date = date;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getReceiptPath() {
        return receiptPath;
    }
    
    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getCategoryColor() {
        return categoryColor;
    }
    
    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
    
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", category='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}