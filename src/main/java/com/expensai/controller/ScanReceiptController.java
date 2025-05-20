package com.expensai.controller;

import com.expensai.model.Category;
import com.expensai.model.CategoryDAO;
import com.expensai.model.Expense;
import com.expensai.model.ExpenseDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class ScanReceiptController {
    @FXML private ImageView cameraPreview;
    @FXML private ImageView filePreview;
    @FXML private Button startCameraButton;
    @FXML private Button captureButton;
    @FXML private Button browseButton;
    @FXML private DatePicker datePicker;
    @FXML private TextField amountField;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private CategoryDAO categoryDAO;
    private ExpenseDAO expenseDAO;
    private File selectedFile;
    
    @FXML
    public void initialize() {
        categoryDAO = new CategoryDAO();
        expenseDAO = new ExpenseDAO();
        
        // Initialize date picker with today's date
        datePicker.setValue(LocalDate.now());
        
        // Load categories
        List<Category> categories = categoryDAO.getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));
        
        // Add input validation
        amountField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                amountField.setText(oldVal);
            }
        });
        
        // Initialize buttons
        startCameraButton.setOnAction(e -> startCamera());
        captureButton.setOnAction(e -> captureImage());
        browseButton.setOnAction(e -> browseFile());
        saveButton.setOnAction(e -> saveReceipt());
        cancelButton.setOnAction(e -> cancel());
    }
    
    private void startCamera() {
        // TODO: Implement camera functionality
        // This would require additional libraries and platform-specific code
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Camera Feature");
        alert.setHeaderText(null);
        alert.setContentText("Camera functionality will be implemented in the next version.");
        alert.showAndWait();
    }
    
    private void captureImage() {
        // TODO: Implement image capture
        // This would be called after the camera is started
    }
    
    private void browseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Receipt Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        selectedFile = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            filePreview.setImage(image);
        }
    }
    
    private void saveReceipt() {
        if (validateInput()) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Category category = categoryDAO.getCategoryById(categoryComboBox.getValue().getId());
                
                Expense expense = new Expense(
                    amount,
                    datePicker.getValue(),
                    category,
                    descriptionArea.getText(),
                    selectedFile != null ? selectedFile.getAbsolutePath() : null
                );
                
                expenseDAO.addExpense(expense);
                
                // Update category spent amount
                categoryDAO.updateCategorySpent(category.getId(), amount);
                
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Receipt saved successfully!");
                alert.showAndWait();
                
                // Clear form
                clearForm();
                
            } catch (NumberFormatException e) {
                showError("Please enter a valid amount");
            }
        }
    }
    
    private boolean validateInput() {
        if (datePicker.getValue() == null) {
            showError("Please select a date");
            return false;
        }
        if (amountField.getText().trim().isEmpty()) {
            showError("Please enter an amount");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showError("Please select a category");
            return false;
        }
        return true;
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void clearForm() {
        datePicker.setValue(LocalDate.now());
        amountField.clear();
        categoryComboBox.setValue(null);
        descriptionArea.clear();
        cameraPreview.setImage(null);
        filePreview.setImage(null);
        selectedFile = null;
    }
    
    private void cancel() {
        // TODO: Implement navigation back to previous screen
    }
} 