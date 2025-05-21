package com.expensai.controller;

import com.expensai.dao.UserDAO;
import com.expensai.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label errorMessageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for user: " + user.getUsername());
            // TODO: Implement session management (e.g., store user ID)
            openDashboard();
        } else {
            errorMessageLabel.setText("Invalid username or password");
        }
    }

    private void handleRegister() {
        System.out.println("Register button clicked");
        openRegistrationView();
    }
    
    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/expensai/view/Dashboard.fxml"));
            Parent root = loader.load();
            
            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("ExpensAI - Dashboard");
            stage.setScene(new Scene(root));
            // The stage is already visible, no need to call show()
            
        } catch (IOException e) {
            e.printStackTrace();
            errorMessageLabel.setText("Error loading dashboard");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    private void openRegistrationView() {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/expensai/view/RegistrationView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("ExpensAI Registration");
            stage.setScene(new Scene(root));
            stage.show();
            closeWindow(); // Close login window

        } catch (IOException ex) {
            ex.printStackTrace();
            errorMessageLabel.setText("Error loading registration view");
        }
    }
} 