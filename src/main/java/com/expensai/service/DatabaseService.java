package com.expensai.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
    
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:expensai.db"; // Using SQLite for simplicity
    
    public void connect() {
        try {
            // Create connection
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connection established");
            
            // Create tables if they don't exist
            createTables();
            
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            
            // Categories table
            stmt.execute("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "color TEXT NOT NULL)");
            
            // Expenses table
            stmt.execute("CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "amount REAL NOT NULL," +
                    "category_id INTEGER NOT NULL," +
                    "description TEXT," +
                    "date DATE NOT NULL," +
                    "receipt_path TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (category_id) REFERENCES categories(id))");
            
            // Budgets table
            stmt.execute("CREATE TABLE IF NOT EXISTS budgets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "category_id INTEGER NOT NULL," +
                    "amount REAL NOT NULL," +
                    "month INTEGER NOT NULL," +
                    "year INTEGER NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (category_id) REFERENCES categories(id))");
            
            // Alerts table
            stmt.execute("CREATE TABLE IF NOT EXISTS alerts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "message TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "is_read BOOLEAN DEFAULT 0," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");
            
            // Insert default categories if they don't exist
            stmt.execute("INSERT OR IGNORE INTO categories (id, name, color) VALUES " +
                    "(1, 'Housing', '#3b82f6')," +
                    "(2, 'Food', '#10b981')," +
                    "(3, 'Transportation', '#6366f1')," +
                    "(4, 'Entertainment', '#f59e0b')," +
                    "(5, 'Utilities', '#ef4444')," +
                    "(6, 'Healthcare', '#8b5cf6')," +
                    "(7, 'Shopping', '#ec4899')," +
                    "(8, 'Education', '#14b8a6')," +
                    "(9, 'Travel', '#f97316')," +
                    "(10, 'Others', '#64748b')");
            
            // Insert a default user if none exists
            stmt.execute("INSERT OR IGNORE INTO users (id, username, email, password) VALUES " +
                    "(1, 'demo', 'demo@expensai.com', 'password')");
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}