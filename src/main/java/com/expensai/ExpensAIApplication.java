package com.expensai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.expensai.service.DatabaseService;

public class ExpensAIApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseService dbService = new DatabaseService();
        dbService.connect(); // Establish database connection

        Parent root = FXMLLoader.load(getClass().getResource("/com/expensai/view/LoginView.fxml"));
        Scene scene = new Scene(root, 400, 300); // Adjust size for login window
        primaryStage.setTitle("ExpensAI - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}