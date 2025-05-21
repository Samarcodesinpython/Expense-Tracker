package com.expensai.controller;

import com.expensai.model.Category;
import com.expensai.model.CategoryDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Label totalIncomeLabel;
    @FXML private Label totalExpensesLabel;
    @FXML private Label remainingBudgetLabel;
    @FXML private PieChart expensePieChart;
    @FXML private LineChart<String, Number> spendingTrendChart;
    @FXML private BarChart<String, Number> monthlyComparisonChart;
    @FXML private AreaChart<String, Number> savingsTrendChart;
    @FXML private VBox alertsContainer;
    @FXML private Button scanReceiptButton;
    @FXML private ToggleButton themeToggleButton;
    @FXML private Button manageCategoriesButton;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN"));

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome back, User!");
        updateCurrencyLabels();
        setupCharts();
        setupAlerts();
        setupButtonHandlers();

        // Apply light theme by default
        applyTheme("light");
    }

    private void updateCurrencyLabels() {
        totalIncomeLabel.setText("Total Income: " + currencyFormat.format(50000));
        totalExpensesLabel.setText("Total Expenses: " + currencyFormat.format(23000));
        remainingBudgetLabel.setText("Remaining Budget: " + currencyFormat.format(27000));
    }

    private void setupCharts() {
        // Pie Chart - Expense Distribution
        expensePieChart.getData().addAll(
            new PieChart.Data("Food", 8000),
            new PieChart.Data("Transport", 3000),
            new PieChart.Data("Shopping", 5000),
            new PieChart.Data("Bills", 7000)
        );

        // Line Chart - Monthly Spending Trend
        XYChart.Series<String, Number> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Expenses");
        expensesSeries.getData().add(new XYChart.Data<>("Jan", 20000));
        expensesSeries.getData().add(new XYChart.Data<>("Feb", 40000));
        expensesSeries.getData().add(new XYChart.Data<>("Mar", 35000));
        expensesSeries.getData().add(new XYChart.Data<>("Apr", 50000));
        expensesSeries.getData().add(new XYChart.Data<>("May", 30000));
        spendingTrendChart.getData().add(expensesSeries);

        // Bar Chart - Monthly Comparison
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        incomeSeries.getData().add(new XYChart.Data<>("Jan", 45000));
        incomeSeries.getData().add(new XYChart.Data<>("Feb", 50000));
        incomeSeries.getData().add(new XYChart.Data<>("Mar", 48000));
        incomeSeries.getData().add(new XYChart.Data<>("Apr", 52000));
        incomeSeries.getData().add(new XYChart.Data<>("May", 49000));

        XYChart.Series<String, Number> expensesBarSeries = new XYChart.Series<>();
        expensesBarSeries.setName("Expenses");
        expensesBarSeries.getData().add(new XYChart.Data<>("Jan", 20000));
        expensesBarSeries.getData().add(new XYChart.Data<>("Feb", 40000));
        expensesBarSeries.getData().add(new XYChart.Data<>("Mar", 35000));
        expensesBarSeries.getData().add(new XYChart.Data<>("Apr", 50000));
        expensesBarSeries.getData().add(new XYChart.Data<>("May", 30000));

        monthlyComparisonChart.getData().add(incomeSeries);
        monthlyComparisonChart.getData().add(expensesBarSeries);

        // Area Chart - Savings Trend
        XYChart.Series<String, Number> savingsSeries = new XYChart.Series<>();
        savingsSeries.setName("Savings");
        savingsSeries.getData().add(new XYChart.Data<>("Jan", 25000));
        savingsSeries.getData().add(new XYChart.Data<>("Feb", 10000));
        savingsSeries.getData().add(new XYChart.Data<>("Mar", 13000));
        savingsSeries.getData().add(new XYChart.Data<>("Apr", 2000));
        savingsSeries.getData().add(new XYChart.Data<>("May", 19000));
        savingsTrendChart.getData().add(savingsSeries);
    }

    private void setupAlerts() {
        alertsContainer.getChildren().clear();
        Label alert1 = new Label("Upcoming Bill: Rent payment of ₹15,000 due in 3 days");
        Label alert2 = new Label("Unusual Spending: Dining expenses 45% higher than average");
        Label alert3 = new Label("AI Insight: You could save ₹8,500/month by reducing subscriptions");
        alertsContainer.getChildren().addAll(alert1, alert2, alert3);
    }

    private void setupButtonHandlers() {
        scanReceiptButton.setOnAction(e -> openCameraModal());
        manageCategoriesButton.setOnAction(e -> openCategoriesManagement());
        themeToggleButton.setOnAction(e -> {
            if (themeToggleButton.isSelected()) {
                applyTheme("dark");
                alertsContainer.getChildren().add(new Label("Dark Mode enabled!"));
            } else {
                applyTheme("light");
                alertsContainer.getChildren().add(new Label("Light Mode enabled!"));
            }
        });
    }

    private void applyTheme(String theme) {
        Scene scene = getCurrentScene();
        if (scene == null) return;
        scene.getStylesheets().clear();
        if ("dark".equals(theme)) {
            scene.getStylesheets().add(getClass().getResource("/com/expensai/view/dark-theme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("/com/expensai/view/light-theme.css").toExternalForm());
        }
    }

    private Scene getCurrentScene() {
        // Try to get the scene from any main control
        if (welcomeLabel != null && welcomeLabel.getScene() != null) return welcomeLabel.getScene();
        if (totalIncomeLabel != null && totalIncomeLabel.getScene() != null) return totalIncomeLabel.getScene();
        return null;
    }

    private void openCameraModal() {
        javafx.stage.Stage modal = new javafx.stage.Stage();
        modal.setTitle("Scan Receipt (Camera)");
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(20);
        vbox.setStyle("-fx-padding: 30; -fx-alignment: center;");
        javafx.scene.control.Label label = new javafx.scene.control.Label("[Camera View Placeholder]");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        javafx.scene.control.Button closeBtn = new javafx.scene.control.Button("Close");
        closeBtn.setOnAction(ev -> modal.close());
        vbox.getChildren().addAll(label, closeBtn);
        modal.setScene(new javafx.scene.Scene(vbox, 350, 200));
        modal.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        modal.showAndWait();
    }

    private void openCategoriesManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/expensai/view/ManageCategories.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Manage Categories");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            // Refresh categories table after dialog closes
            refreshCategoriesTable();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not open Categories Management");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void refreshCategoriesTable() {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.getAllCategories();
        
        // Clear existing data
        expensePieChart.getData().clear();
        
        // Add new data from database
        for (Category category : categories) {
            expensePieChart.getData().add(new PieChart.Data(
                category.getName(),
                category.getSpent()
            ));
        }
        
        // Update currency labels with total spent
        double totalSpent = categories.stream()
            .mapToDouble(Category::getSpent)
            .sum();
        totalExpensesLabel.setText("Total Expenses: " + currencyFormat.format(totalSpent));
        
        // Update remaining budget
        double totalBudget = categories.stream()
            .mapToDouble(Category::getBudget)
            .sum();
        double remaining = totalBudget - totalSpent;
        remainingBudgetLabel.setText("Remaining Budget: " + currencyFormat.format(remaining));
    }
}