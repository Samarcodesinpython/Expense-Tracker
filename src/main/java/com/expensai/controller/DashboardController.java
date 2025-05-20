package com.expensai.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Label totalIncomeLabel;
    @FXML private Label totalExpensesLabel;
    @FXML private Label remainingBudgetLabel;
    @FXML private Label aiPredictionLabel;
    @FXML private PieChart expensePieChart;
    @FXML private LineChart<String, Number> spendingTrendChart;
    @FXML private VBox alertsContainer;
    @FXML private Button addExpenseButton;
    @FXML private Button scanReceiptButton;
    @FXML private ToggleButton themeToggleButton;
    @FXML private Button scanCameraButton;

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome back, User!");
        totalIncomeLabel.setText("Total Income: $5,000.00");
        totalExpensesLabel.setText("Total Expenses: $2,300.00");
        remainingBudgetLabel.setText("Remaining Budget: $2,700.00");
        aiPredictionLabel.setText("AI Prediction: $2,450.00");

        // Apply light theme by default
        applyTheme("light");

        // PieChart dummy data
        expensePieChart.getData().addAll(
            new PieChart.Data("Food", 800),
            new PieChart.Data("Transport", 300),
            new PieChart.Data("Shopping", 500),
            new PieChart.Data("Bills", 700)
        );

        // LineChart dummy data
        XYChart.Series<String, Number> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Expenses");
        expensesSeries.getData().add(new XYChart.Data<>("Jan", 200));
        expensesSeries.getData().add(new XYChart.Data<>("Feb", 400));
        expensesSeries.getData().add(new XYChart.Data<>("Mar", 350));
        expensesSeries.getData().add(new XYChart.Data<>("Apr", 500));
        expensesSeries.getData().add(new XYChart.Data<>("May", 300));
        spendingTrendChart.getData().add(expensesSeries);

        // Alerts
        alertsContainer.getChildren().clear();
        Label alert1 = new Label("Upcoming Bill: Rent payment due in 3 days");
        Label alert2 = new Label("Unusual Spending: Dining expenses 45% higher than average");
        Label alert3 = new Label("AI Insight: You could save $85/month by reducing subscriptions");
        alertsContainer.getChildren().addAll(alert1, alert2, alert3);

        addExpenseButton.setOnAction(e -> alertsContainer.getChildren().add(new Label("Add Expense clicked!")));
        scanReceiptButton.setOnAction(e -> alertsContainer.getChildren().add(new Label("Scan Receipt clicked!")));
        scanCameraButton.setOnAction(e -> openCameraModal());
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
}