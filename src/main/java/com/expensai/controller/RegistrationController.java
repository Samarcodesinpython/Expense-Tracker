package com.expensai.controller;

import com.expensai.dao.UserDAO;
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
import javafx.scene.control.Alert;

public class RegistrationController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Button loginButton;
    @FXML private Label errorMessageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> handleRegistration());
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessageLabel.setText("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Passwords do not match");
            return;
        }
        
        // Placeholder check to use userDAO
        if (userDAO.getUserByUsername(username) != null) {
            errorMessageLabel.setText("Username already exists");
            return;
        }

        System.out.println("Attempting to register user:");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password); // Remember to hash passwords in a real app!

        // Assuming registration is successful for now
        showAlert("Registration Successful!", "You can now log in.");
        openLoginView();
    }

    private void handleLogin() {
        System.out.println("Login button clicked from registration");
        openLoginView();
    }

    private void openLoginView() {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/expensai/view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("ExpensAI Login");
            stage.setScene(new Scene(root));
            stage.show();
            closeWindow(); // Close registration window

        } catch (IOException ex) {
            ex.printStackTrace();
            errorMessageLabel.setText("Error loading login view");
        }
    }
    
    private void closeWindow() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        // Basic alert for now, could use a dedicated dialog service
        // Using javafx.scene.control.Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 