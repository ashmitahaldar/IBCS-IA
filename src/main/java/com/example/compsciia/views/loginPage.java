package com.example.compsciia.views;
import com.example.compsciia.compsciia;
import com.example.compsciia.util.userService;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;

import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(220.0);
        emailTextField.setLayoutY(230.0);
        emailTextField.setPrefHeight(25.0);
        emailTextField.setPrefWidth(240.0);
        emailTextField.setPromptText("Email Address");
        emailTextField.setFont(new Font(15.0));
        emailTextField.getStyleClass().add("textfield-design");

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(220.0);
        passwordField.setLayoutY(280.0);
        passwordField.setPrefHeight(25.0);
        passwordField.setPrefWidth(240.0);
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(15.0));
        passwordField.getStyleClass().add("textfield-design");

        ChoiceBox<String> accountTypeChoiceBox = new ChoiceBox<>();
        accountTypeChoiceBox.setLayoutX(220.0);
        accountTypeChoiceBox.setLayoutY(180.0);
        accountTypeChoiceBox.setPrefWidth(240.0);
        accountTypeChoiceBox.setItems(FXCollections.observableArrayList("Client", "Administrator"));

        Label accountTypeLabel = new Label("Account Type");
        accountTypeLabel.setLayoutX(220.0);
        accountTypeLabel.setLayoutY(150.0);
        accountTypeLabel.setFont(new Font(18.0));

        Button loginButton = new Button("Login");
        loginButton.setId("loginPage-login");
        loginButton.setLayoutX(220.0);
        loginButton.setLayoutY(330.0);
        loginButton.setPrefHeight(30.0);
        loginButton.setPrefWidth(80.0);
        loginButton.getStyleClass().add("button-design");
        loginButton.setOnAction(event -> {
            validate(emailTextField.getText(), passwordField.getText(), stage);
            emailTextField.clear();
            passwordField.clear();
        });

        Button signUpButton = new Button("Create Account");
        signUpButton.setId("loginPage-signup");
        signUpButton.setLayoutX(340.0);
        signUpButton.setLayoutY(330.0);
        signUpButton.setPrefHeight(30.0);
        signUpButton.setPrefWidth(120.0);
        signUpButton.getStyleClass().add("button-design");
        signUpButton.setOnAction(e -> stage.setScene(signUpPage.createScene(stage, new loginPage())));

        centerAnchorPane.getChildren().addAll(loginLabel, emailTextField, passwordField,
                accountTypeChoiceBox, accountTypeLabel, loginButton, signUpButton);

        // Set components in the BorderPane
        root.setLeft(leftAnchorPane);
        root.setCenter(centerAnchorPane);

        Scene scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(compsciia.class.getResource("/com/example/compsciia/stylesheet.css")).toExternalForm());
        return scene;
    }

    private void validate(String email, String password, Stage stage) {
        if (isValidEmail(email) && isValidPassword(password)){
            if (!isValidUser(email, password)) {
                showInvalidUserPopup();
            } else if (isValidUser(email, password)){
                System.out.println("User is valid");
                stage.setScene(Dashboard.createScene(stage, new loginPage(), userService.getUserFromDatabase(email, password)));
            }
        } else {
            if (!isValidEmail(email)){
                showInvalidEmailPopup();
            }
            if (!isValidPassword(password)){
                showInvalidPasswordPopup();
            }
        }
    }
    private boolean isValidUser(String email, String password) {
        return userService.getUserFromDatabase(email, password) != null;
    }
    private void showInvalidUserPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid User");
        alert.setHeaderText(null);
        alert.setContentText("A user with the entered credentials does not exist.");

        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a simple email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String password) {
        // Check for at least 8 characters
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()-_+=<>?/{}\\[\\]~].*")) {
            return false;
        }
        return true;
    }
    private void showInvalidEmailPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Email");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid email address.");
        alert.showAndWait();
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            // User clicked OK, you can perform additional actions if needed
//        }
    }
    private void showInvalidPasswordPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Password");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a password with at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.");

        alert.showAndWait();
    }
}
