package com.example.compsciia;

import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.compsciia.views.loginPage;
import com.example.compsciia.views.signUpPage;
import com.example.compsciia.views.Dashboard;

import java.io.IOException;

public class compsciia extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(compsciia.class.getResource("home.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
//        stage.setScene(scene);
        loginPage loginPage = new loginPage();
        signUpPage signUpPage = new signUpPage();
        Scene loginScene = loginPage.createScene(stage, signUpPage);
//        Scene dashboardScene = Dashboard.createScene(stage, loginPage);
        stage.setTitle("Computer Science IA");
        stage.setScene(loginScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}