package com.expensai.controller;

import com.expensai.dao.CategoryDAO;
import com.expensai.dao.ExpenseDAO;
import com.expensai.model.Category;
import com.expensai.model.Expense;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddExpenseController implements Initializable {
    
    @FXML private TextField amountField;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker datePicker;
    @FXML private Label receiptPathLabel;
    @FXML private Button uploadReceiptButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private final ExpenseDAO expenseDAO = new ExpenseDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private File selectedReceiptFile;
    private final int currentUserId = 1; // Default user for demo
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default date to today
        datePicker.setValue(LocalDate.now());
        
        // Load categories
        loadCategories();
        
        // Set up button handlers
        setupButtonHandlers();
    }
    
    private void loadCategories() {
        List<Category> categories = categoryDAO.getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));
        
        // Select first category by default
        if (!categories.isEmpty()) {
            categoryComboBox.getSelectionModel().selectFirst();
        }
    }
    
    private void setupButtonHandlers() {
        // Upload Receipt button
        uploadReceiptButton.setOnAction(event -> uploadReceipt());
        
        // Save button
        saveButton.setOnAction(event -> saveExpense());
        
        // Cancel button
        cancelButton.setOnAction(event -> closeModal());
    }
    
    private void uploadReceipt() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Receipt Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        // Show file chooser dialog
        selectedReceiptFile = fileChooser.showOpenDialog(uploadReceiptButton.getScene().getWindow());
        
        if (selectedReceiptFile != null) {
            receiptPathLabel.setText(selectedReceiptFile.getName());
        }
    }
    
    private void saveExpense() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }
            
            // Create expense object
            Expense expense = new Expense();
            expense.setUserId(currentUserId);
            expense.setAmount(Double.parseDouble(amountField.getText()));
            expense.setCategoryId(categoryComboBox.getValue().getId());
            expense.setDescription(descriptionArea.getText());
            expense.setDate(datePicker.getValue());
            
            // Set receipt path if a file was selected
            if (selectedReceiptFile != null) {
                expense.setReceiptPath(selectedReceiptFile.getAbsolutePath());
            }
            
            // Save expense to database
            boolean success = expenseDAO.addExpense(expense);
            
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Expense added successfully.");
                closeModal();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add expense. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid amount.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }
    
    private boolean validateInput() {
        // Check amount
        if (amountField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please enter an amount.");
            return false;
        }
        
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Amount", "Amount must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Amount", "Please enter a valid number for amount.");
            return false;
        }
        
        // Check category
        if (categoryComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please select a category.");
            return false;
        }
        
        // Check date
        if (datePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please select a date.");
            return false;
        }
        
        return true;
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void closeModal() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}