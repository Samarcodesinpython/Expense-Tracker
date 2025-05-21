package com.expensai.dao;

import com.expensai.model.User;
import com.expensai.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseService.getConnection();
            if (conn == null || conn.isClosed()) {
                 System.err.println("Database connection is not available.");
                 return null;
            }
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); // In a real app, never retrieve or store passwords in plain text!
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by username: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // Do NOT close the connection here if it's a shared static connection
                // If we move to per-operation connections, we would close it here.
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    // TODO: Implement methods for user registration, getting user by email, etc.
} 