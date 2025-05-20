package com.expensai.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private static final String DB_URL = "jdbc:sqlite:expensai.db";
    
    public ExpenseDAO() {
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount REAL NOT NULL,
                date TEXT NOT NULL,
                category_id INTEGER NOT NULL,
                description TEXT,
                receipt_path TEXT,
                FOREIGN KEY (category_id) REFERENCES categories (id)
            )
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.*, c.name as category_name, c.color as category_color, c.icon as category_icon " +
                    "FROM expenses e " +
                    "JOIN categories c ON e.category_id = c.id " +
                    "ORDER BY e.date DESC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("category_color"),
                    rs.getString("category_icon"),
                    0.0
                );
                
                Expense expense = new Expense(
                    rs.getInt("id"),
                    rs.getDouble("amount"),
                    LocalDate.parse(rs.getString("date")),
                    category,
                    rs.getString("description"),
                    rs.getString("receipt_path")
                );
                
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return expenses;
    }
    
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (amount, date, category_id, description, receipt_path) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getDate().toString());
            pstmt.setInt(3, expense.getCategory().getId());
            pstmt.setString(4, expense.getDescription());
            pstmt.setString(5, expense.getReceiptPath());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET amount = ?, date = ?, category_id = ?, description = ?, receipt_path = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getDate().toString());
            pstmt.setInt(3, expense.getCategory().getId());
            pstmt.setString(4, expense.getDescription());
            pstmt.setString(5, expense.getReceiptPath());
            pstmt.setInt(6, expense.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 