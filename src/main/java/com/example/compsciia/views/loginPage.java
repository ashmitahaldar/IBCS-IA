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

        // Left AnchorPane (Image and Text)
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setPrefSize(330.0, 540.0);
        leftAnchorPane.getStyleClass().add("login-bg");

        // Welcome Text
        Label welcomeText = new Label();
        welcomeText.setLayoutX(100.0);
        welcomeText.setLayoutY(289.0);
        welcomeText.setPrefHeight(50.0);
        welcomeText.setPrefWidth(200);
        welcomeText.setText("Clientify");
        welcomeText.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
        welcomeText.setTextFill(javafx.scene.paint.Color.WHITE);
        welcomeText.setWrapText(true);
        welcomeText.setFont(new Font(40.0));

        // Description Text
        Label descriptionText = new Label();
        descriptionText.setLayoutX(58.0);
        descriptionText.setLayoutY(346.0);
        descriptionText.setPrefHeight(100.0);
        descriptionText.setPrefWidth(220.0);
        descriptionText.setText("Investment portfolio client management and XIRR report system.");
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        descriptionText.setTextFill(javafx.scene.paint.Color.WHITE);
        descriptionText.setTextOverrun(javafx.scene.control.OverrunStyle.CENTER_ELLIPSIS);
        descriptionText.setWrapText(true);
        descriptionText.setFont(new Font(19.0));

        // Image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150.0);
        imageView.setFitWidth(200.0);
        imageView.setLayoutX(90.0);
        imageView.setLayoutY(120.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new javafx.scene.image.Image(Objects.requireNonNull(compsciia.class.getResource("appLogoImage.png")).toExternalForm(), false));

        // Set components in the Left AnchorPane
        leftAnchorPane.getChildren().addAll(welcomeText, descriptionText, imageView);

        // Center AnchorPane (Login Form)
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefSize(630.0, 540.0);
        centerAnchorPane.getStyleClass().add("app-bg");

        // Login Label
        Label loginLabel = new Label("Login");
        loginLabel.setLayoutX(300.0);
        loginLabel.setLayoutY(72.0);
        loginLabel.setPrefHeight(65.0);
        loginLabel.setPrefWidth(84.0);
        loginLabel.setFont(new Font("Arial", 33.0));

        // y diff between text label and text field - 30px
        // y diff between text field and text label - 50px
        // y diff between text field and button - 50px

        // Email Address Text Label
        Label emailTextLabel = new Label("Email Address");
        emailTextLabel.setLayoutX(220.0);
        emailTextLabel.setLayoutY(150.0);
        emailTextLabel.setFont(new Font(18.0));

        // Email Address Text Field
        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(220.0);
        emailTextField.setLayoutY(180.0);
        emailTextField.setPrefHeight(25.0);
        emailTextField.setPrefWidth(240.0);
        emailTextField.setPromptText("Email Address");
        emailTextField.setFont(new Font(15.0));
        emailTextField.getStyleClass().add("textfield-design");

        // Password Text Label
        Label passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(220.0);
        passwordLabel.setLayoutY(230.0);
        passwordLabel.setFont(new Font(18.0));

        // Password Text Field
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(220.0);
        passwordField.setLayoutY(260.0);
        passwordField.setPrefHeight(25.0);
        passwordField.setPrefWidth(240.0);
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(15.0));
        passwordField.getStyleClass().add("textfield-design");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setId("loginPage-login");
        loginButton.setLayoutX(220.0);
        loginButton.setLayoutY(310.0);
        loginButton.setPrefHeight(30.0);
        loginButton.setPrefWidth(80.0);
        loginButton.getStyleClass().add("button-design");
        // When the login button is clicked, validates the user's credentials and logs them in if they are valid
        loginButton.setOnAction(event -> {
            try {
                validate(emailTextField.getText(), passwordField.getText(), stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            emailTextField.clear();
            passwordField.clear();
        });

        // Sign Up Button
        Button signUpButton = new Button("Create Account");
        signUpButton.setId("loginPage-signup");
        signUpButton.setLayoutX(340.0);
        signUpButton.setLayoutY(310.0);
        signUpButton.setPrefHeight(30.0);
        signUpButton.setPrefWidth(120.0);
        signUpButton.getStyleClass().add("button-design");
        // When the sign up button is clicked, switches the scene to the sign up page
        signUpButton.setOnAction(e -> stage.setScene(signUpPage.createScene(stage, new loginPage())));

        // Forgot Password Hyperlink
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setLayoutX(300.0);
        forgotPasswordLink.setLayoutY(450.0);
        forgotPasswordLink.setPrefHeight(30.0);
        forgotPasswordLink.setPrefWidth(120.0);
        // When the forgot password hyperlink is clicked, opens a dialog to enter the user's email address
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
                        // If user enters email, calls the forgotPassword method
                        forgotPassword(emailTextField1.getText());
                    }
                    return null;
                });
                dialog.showAndWait();
        });

        // Set components in the Center AnchorPane
        centerAnchorPane.getChildren().addAll(loginLabel, emailTextField, passwordField,
                 emailTextLabel, passwordLabel, loginButton, signUpButton, forgotPasswordLink);

        // Set components in the BorderPane
        root.setLeft(leftAnchorPane);
        root.setCenter(centerAnchorPane);

        // Returns the scene
        Scene scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(compsciia.class.getResource("/com/example/compsciia/stylesheet.css")).toExternalForm());
        return scene;
    }

    // Validates the user's credentials and logs them in if they are valid
    private void validate(String email, String password, Stage stage) throws IOException {
        if (Validators.isValidUser(email, password)){
            System.out.println("User is valid");
            User user = UserService.getUserFromDatabase(email, password);
            // Utilizes the Task class to run the switchSceneToDashboard task in the background, utilizing threading
            Task<Scene> switchSceneToDashboard = new Task<Scene>() {
                @Override
                protected Scene call() throws Exception {
                    loginPage loginpage = new loginPage();
                    int userId = user.getId();
                    return Dashboard.createScene(stage, loginpage, userId);
                }
            };
            // When the task is running, shows a popup to inform the user that they are being logged in
            switchSceneToDashboard.setOnRunning(e -> showLoginPopup(user));
            // When the task is succeeded, switches the scene to the dashboard
            switchSceneToDashboard.setOnSucceeded(e ->
                    Platform.runLater(() -> {
                        try {
                            stage.setScene(switchSceneToDashboard.getValue());
                        } catch (Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    }));
            // Starts the task
            new Thread(switchSceneToDashboard).start();
        } else {
            // If the user is not valid, shows a popup to inform the user that their credentials are invalid
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

    // Shows a popup to inform the user that they are being logged in
    private void showLoginPopup(User user){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Success");
        alert.setHeaderText(null);
        alert.setContentText("Welcome " + user.getUsername() + "! You will now be logged in.");

        alert.showAndWait();
    }

    // Sends an email to the user with their password if they have forgotten it
    private void forgotPassword(String email) {
        if (!Validators.isValidEmail(email)) { // If the email is invalid, shows a popup to inform the user
            Validators.showInvalidEmailPopup();
        } else if (!UserService.checkIfUserExists(email)) { // If the user does not exist, shows a popup to inform the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Forgot Password");
            alert.setHeaderText(null);
            alert.setContentText("The user does not exist.");
            alert.showAndWait();
        } else if (Validators.isValidEmail(email) && UserService.checkIfUserExists(email)) { // If the user exists, sends an email to the user with their password
            UserService.forgotPassword(email);
        }
    }
}
