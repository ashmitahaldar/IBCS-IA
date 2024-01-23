package com.example.compsciia.views;
import com.example.compsciia.compsciia;
import com.example.compsciia.models.User;
import com.example.compsciia.util.UserService;
import com.example.compsciia.util.Validators;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginPage {
    public Scene createScene(Stage stage, signUpPage signUpPage) {
        BorderPane root = new BorderPane();

        // Left AnchorPane
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setPrefSize(330.0, 540.0);
        leftAnchorPane.getStyleClass().add("login-bg");
        //leftAnchorPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ebab7, #a2f5c0);");
        //leftAnchorPane.setStyle("-fx-background-color: #4ebab7;");

        Label welcomeText = new Label("App");
        welcomeText.setLayoutX(126.0);
        welcomeText.setLayoutY(289.0);
        welcomeText.setPrefHeight(50.0);
        welcomeText.setPrefWidth(74.0);
        welcomeText.setText("App");
        welcomeText.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
        welcomeText.setTextFill(javafx.scene.paint.Color.WHITE);
        welcomeText.setWrapText(true);
        welcomeText.setFont(new Font(40.0));

        Label descriptionText = new Label("Investment portfolio client management system.");
        descriptionText.setLayoutX(58.0);
        descriptionText.setLayoutY(346.0);
        descriptionText.setPrefHeight(56.0);
        descriptionText.setPrefWidth(213.0);
        descriptionText.setText("Investment portfolio client management system.");
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        descriptionText.setTextFill(javafx.scene.paint.Color.WHITE);
        descriptionText.setTextOverrun(javafx.scene.control.OverrunStyle.CENTER_ELLIPSIS);
        descriptionText.setWrapText(true);
        descriptionText.setFont(new Font(19.0));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imageView.setLayoutX(65.0);
        imageView.setLayoutY(97.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        leftAnchorPane.getChildren().addAll(welcomeText, descriptionText, imageView);

        // Center AnchorPane
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefSize(630.0, 540.0);
        centerAnchorPane.getStyleClass().add("app-bg");

        Label loginLabel = new Label("Login");
        loginLabel.setLayoutX(300.0);
        loginLabel.setLayoutY(72.0);
        loginLabel.setPrefHeight(65.0);
        loginLabel.setPrefWidth(84.0);
        loginLabel.setFont(new Font("Arial", 33.0));

        // y diff between text label and text field - 30px
        // y diff between text field and text label - 50px
        // y diff between text field and button - 50px

        Label emailTextLabel = new Label("Email Address");
        emailTextLabel.setLayoutX(220.0);
        emailTextLabel.setLayoutY(150.0);
        emailTextLabel.setFont(new Font(18.0));

        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(220.0);
        emailTextField.setLayoutY(180.0);
        emailTextField.setPrefHeight(25.0);
        emailTextField.setPrefWidth(240.0);
        emailTextField.setPromptText("Email Address");
        emailTextField.setFont(new Font(15.0));
        emailTextField.getStyleClass().add("textfield-design");

        Label passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(220.0);
        passwordLabel.setLayoutY(230.0);
        passwordLabel.setFont(new Font(18.0));

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(220.0);
        passwordField.setLayoutY(260.0);
        passwordField.setPrefHeight(25.0);
        passwordField.setPrefWidth(240.0);
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(15.0));
        passwordField.getStyleClass().add("textfield-design");

        Button loginButton = new Button("Login");
        loginButton.setId("loginPage-login");
        loginButton.setLayoutX(220.0);
        loginButton.setLayoutY(310.0);
        loginButton.setPrefHeight(30.0);
        loginButton.setPrefWidth(80.0);
        loginButton.getStyleClass().add("button-design");
        loginButton.setOnAction(event -> {
            try {
                validate(emailTextField.getText(), passwordField.getText(), stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emailTextField.clear();
            passwordField.clear();
        });

        Button signUpButton = new Button("Create Account");
        signUpButton.setId("loginPage-signup");
        signUpButton.setLayoutX(340.0);
        signUpButton.setLayoutY(310.0);
        signUpButton.setPrefHeight(30.0);
        signUpButton.setPrefWidth(120.0);
        signUpButton.getStyleClass().add("button-design");
        signUpButton.setOnAction(e -> stage.setScene(signUpPage.createScene(stage, new loginPage())));

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setLayoutX(300.0);
        forgotPasswordLink.setLayoutY(450.0);
        forgotPasswordLink.setPrefHeight(30.0);
        forgotPasswordLink.setPrefWidth(120.0);
        forgotPasswordLink.setOnAction(e -> {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Forgot Password");
                dialog.setHeaderText("Enter your email address to receive your password");
                dialog.setResizable(true);
                TextField emailTextField1 = new TextField();
                emailTextField1.setPromptText("Email Address");
                ButtonType resetButtonType = new ButtonType("Send Email", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(resetButtonType, ButtonType.CANCEL);
                dialog.getDialogPane().setContent(emailTextField1);
                Platform.runLater(emailTextField1::requestFocus);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == resetButtonType) {
                        forgotPassword(emailTextField1.getText());
                    }
                    return null;
                });
                dialog.showAndWait();
        });

        centerAnchorPane.getChildren().addAll(loginLabel, emailTextField, passwordField,
                 emailTextLabel, passwordLabel, loginButton, signUpButton, forgotPasswordLink);

        // Set components in the BorderPane
        root.setLeft(leftAnchorPane);
        root.setCenter(centerAnchorPane);

        Scene scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(compsciia.class.getResource("/com/example/compsciia/stylesheet.css")).toExternalForm());
        return scene;
    }

    private void validate(String email, String password, Stage stage) throws IOException {
        if (Validators.isValidUser(email, password)){
            System.out.println("User is valid");
            User user = UserService.getUserFromDatabase(email, password);
            Task<Scene> switchSceneToDashboard = new Task<Scene>() {
                @Override
                protected Scene call() throws Exception {
                    loginPage loginpage = new loginPage();
                    int userId = user.getId();
                    return Dashboard.createScene(stage, loginpage, userId);
                }
            };
            switchSceneToDashboard.setOnRunning(e -> showLoginPopup(user));
            switchSceneToDashboard.setOnSucceeded(e ->
                    Platform.runLater(() -> {
                        try {
                            stage.setScene(switchSceneToDashboard.getValue());
                        } catch (Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    }));
            new Thread(switchSceneToDashboard).start();
        } else {
            if (!Validators.isValidEmail(email) || !Validators.isValidPassword(password)){
                if (!Validators.isValidEmail(email)){
                    Validators.showInvalidEmailPopup();
                }
                if (!Validators.isValidPassword(password)){
                    Validators.showInvalidPasswordPopup();
                }
            } else {
                Validators.showInvalidUserPopup();
            }
        }
    }
    private void showLoginPopup(User user){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Success");
        alert.setHeaderText(null);
        alert.setContentText("Welcome " + user.getUsername() + "! You will now be logged in.");

        alert.showAndWait();
    }

    private void forgotPassword(String email) {
        if (!Validators.isValidEmail(email)) {
            Validators.showInvalidEmailPopup();
        } else if (!UserService.checkIfUserExists(email)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Forgot Password");
            alert.setHeaderText(null);
            alert.setContentText("The user does not exist.");
            alert.showAndWait();
        } else if (Validators.isValidEmail(email) && UserService.checkIfUserExists(email)) {
            UserService.forgotPassword(email);
        }
    }
}
