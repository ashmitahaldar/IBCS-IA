package com.example.compsciia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.compsciia.views.loginPage;
import com.example.compsciia.views.signUpPage;

import java.io.IOException;

public class compsciia extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create the login page and set the stage
        loginPage loginPage = new loginPage();
        signUpPage signUpPage = new signUpPage();
        Scene loginScene = loginPage.createScene(stage, signUpPage);
        stage.setTitle("Computer Science IA");
        stage.setScene(loginScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}