package com.expensai.controller;

import com.expensai.model.Category;
import com.expensai.model.CategoryDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.Optional;

public class ManageCategoriesController {
    @FXML private TableView<Category> categoryTable;
    @FXML private TableColumn<Category, String> nameColumn;
    @FXML private TableColumn<Category, String> colorColumn;
    @FXML private TableColumn<Category, String> iconColumn;
    @FXML private TableColumn<Category, Double> budgetColumn;
    @FXML private TableColumn<Category, Double> spentColumn;
    @FXML private TableColumn<Category, Double> remainingColumn;
    
    @FXML private TextField nameField;
    @FXML private ColorPicker colorPicker;
    @FXML private ComboBox<String> iconComboBox;
    @FXML private TextField budgetField;
    
    private CategoryDAO categoryDAO;
    private ObservableList<Category> categories;
    
    @FXML
    public void initialize() {
        categoryDAO = new CategoryDAO();
        categories = FXCollections.observableArrayList();
        
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        iconColumn.setCellValueFactory(new PropertyValueFactory<>("icon"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        spentColumn.setCellValueFactory(new PropertyValueFactory<>("spent"));
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        
        // Custom cell factory for color column
        colorColumn.setCellFactory(col -> new TableCell<Category, String>() {
            @Override
            protected void updateItem(String color, boolean empty) {
                super.updateItem(color, empty);
                if (empty || color == null) {
                    setGraphic(null);
                } else {
                    Rectangle rect = new Rectangle(20, 20);
                    rect.setFill(Color.valueOf(color));
                    setGraphic(rect);
                }
            }
        });
        
        // Custom cell factory for icon column
        iconColumn.setCellFactory(col -> new TableCell<Category, String>() {
            @Override
            protected void updateItem(String icon, boolean empty) {
                super.updateItem(icon, empty);
                if (empty || icon == null) {
                    setGraphic(null);
                } else {
                    Label iconLabel = new Label(icon);
                    iconLabel.setStyle("-fx-font-size: 16px;");
                    setGraphic(iconLabel);
                }
            }
        });
        
        // Initialize icon combo box with common icons
        iconComboBox.setItems(FXCollections.observableArrayList(
            "🛒", "🍽️", "🚗", "🏠", "💼", "🎮", "📱", "✈️", "🏥", "🎓"
        ));
        
        // Load categories
        loadCategories();
        
        // Add selection listener
        categoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }
    
    private void loadCategories() {
        List<Category> categoryList = categoryDAO.getAllCategories();
        categories.setAll(categoryList);
        categoryTable.setItems(categories);
    }
    
    private void populateFields(Category category) {
        nameField.setText(category.getName());
        colorPicker.setValue(category.getColorAsColor());
        iconComboBox.setValue(category.getIcon());
        budgetField.setText(String.valueOf(category.getBudget()));
    }
    
    @FXML
    private void handleAddCategory() {
        if (validateInput()) {
            Category category = new Category(
                nameField.getText(),
                colorPicker.getValue().toString(),
                iconComboBox.getValue(),
                Double.parseDouble(budgetField.getText())
            );
            
            categoryDAO.addCategory(category);
            loadCategories();
            clearFields();
        }
    }
    
    @FXML
    private void handleUpdateCategory() {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory != null && validateInput()) {
            selectedCategory.setName(nameField.getText());
            selectedCategory.setColor(colorPicker.getValue().toString());
            selectedCategory.setIcon(iconComboBox.getValue());
            selectedCategory.setBudget(Double.parseDouble(budgetField.getText()));
            
            categoryDAO.updateCategory(selectedCategory);
            loadCategories();
            clearFields();
        }
    }
    
    @FXML
    private void handleDeleteCategory() {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Category");
            alert.setContentText("Are you sure you want to delete this category?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                categoryDAO.deleteCategory(selectedCategory.getId());
                loadCategories();
                clearFields();
            }
        }
    }
    
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Name is required");
            return false;
        }
        if (iconComboBox.getValue() == null) {
            showError("Please select an icon");
            return false;
        }
        try {
            double budget = Double.parseDouble(budgetField.getText());
            if (budget < 0) {
                showError("Budget cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid budget amount");
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
    
    private void clearFields() {
        nameField.clear();
        colorPicker.setValue(Color.WHITE);
        iconComboBox.setValue(null);
        budgetField.clear();
        categoryTable.getSelectionModel().clearSelection();
    }
} 