package com.example.compsciia.views;
import com.example.compsciia.compsciia;
import com.example.compsciia.util.UserService;
import com.example.compsciia.util.EmailService;
import com.example.compsciia.util.Validators;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUpPage {
    public Scene createScene(Stage stage, loginPage loginPage) {
        BorderPane root = new BorderPane();

        // Left AnchorPane
        AnchorPane rightAnchorPane = new AnchorPane();
        rightAnchorPane.setPrefSize(330.0, 540.0);
        rightAnchorPane.getStyleClass().add("login-bg");

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
        descriptionText.setPrefHeight(100);
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

        // Add components to the right AnchorPane
        rightAnchorPane.getChildren().addAll(welcomeText, descriptionText, imageView);

        // Center AnchorPane
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefSize(630.0, 540.0);
        centerAnchorPane.getStyleClass().add("app-bg");

        // Sign Up Label
        Label signUpLabel = new Label("Sign Up");
        signUpLabel.setLayoutX(280.0);
        signUpLabel.setLayoutY(72.0);
        signUpLabel.setPrefHeight(65.0);
        signUpLabel.setPrefWidth(150.0);
        signUpLabel.setFont(new Font("Arial", 33.0));

        // y diff between text label and text field - 30px
        // y diff between text field and text label - 50px
        // y diff between text field and button - 50px

        // Username Label
        Label usernameLabel = new Label("Username");
        usernameLabel.setLayoutX(220.0);
        usernameLabel.setLayoutY(150.0);
        usernameLabel.setFont(new Font(18.0));

        // Username Text Field
        TextField usernameField = new TextField();
        usernameField.setLayoutX(220.0);
        usernameField.setLayoutY(180.0);
        usernameField.setPrefWidth(240.0);
        usernameField.setPromptText("Username");
        usernameField.setFont(new Font(15.0));
        usernameField.getStyleClass().add("textfield-design");

        // Email Label
        Label emailTextLabel = new Label("Email Address");
        emailTextLabel.setLayoutX(220.0);
        emailTextLabel.setLayoutY(230.0);
        emailTextLabel.setFont(new Font(18.0));

        // Email Text Field
        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(220.0);
        emailTextField.setLayoutY(260.0);
        emailTextField.setPrefHeight(25.0);
        emailTextField.setPrefWidth(240.0);
        emailTextField.setPromptText("Email Address");
        emailTextField.setFont(new Font(15.0));
        emailTextField.getStyleClass().add("textfield-design");

        // Password Label
        Label passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(220.0);
        passwordLabel.setLayoutY(310.0);
        passwordLabel.setFont(new Font(18.0));

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(220.0);
        passwordField.setLayoutY(340.0);
        passwordField.setPrefHeight(25.0);
        passwordField.setPrefWidth(240.0);
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(15.0));
        passwordField.getStyleClass().add("textfield-design");

        // Login Button
        Button loginButton = new Button("Back to Login");
        loginButton.setId("loginPage-login");
        loginButton.setLayoutX(220.0);
        loginButton.setLayoutY(390.0);
        loginButton.setPrefHeight(30.0);
        loginButton.setPrefWidth(100.0);
        loginButton.getStyleClass().add("button-design");
        // Set the action for the login button
        loginButton.setOnAction(e -> stage.setScene(loginPage.createScene(stage, new signUpPage())));

        // Sign Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setId("loginPage-signup");
        signUpButton.setLayoutX(370.0);
        signUpButton.setLayoutY(390.0);
        signUpButton.setPrefHeight(30.0);
        signUpButton.setPrefWidth(90.0);
        signUpButton.getStyleClass().add("button-design");
        // If the sign up button is clicked, validate the input fields
        signUpButton.setOnAction(event -> {
            validate(usernameField.getText(), emailTextField.getText(),passwordField.getText());
            usernameField.clear();
            emailTextField.clear();
            passwordField.clear();
        });

        // Add components to the center AnchorPane
        centerAnchorPane.getChildren().addAll(signUpLabel, emailTextLabel, emailTextField, passwordLabel, passwordField,
                usernameField, usernameLabel, loginButton, signUpButton);

        // Set components in the BorderPane
        root.setRight(rightAnchorPane);
        root.setCenter(centerAnchorPane);

        // Returns the scene
        Scene scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(compsciia.class.getResource("/com/example/compsciia/stylesheet.css")).toExternalForm());
        return scene;
    }

    // Validate the input fields and write the user to the database if the input is valid
    private void validate(String username, String email, String password) {
        if (!Validators.isValidEmail(email)) { // if the email is invalid, show an error message
            Validators.showInvalidEmailPopup();
        }
        if (!Validators.isValidPassword(password)) { // if the password is invalid, show an error message
            Validators.showInvalidPasswordPopup();
        }
        if (!Validators.isValidUsername(username)){ // if the username is invalid, show an error message
            Validators.showInvalidUsernamePopup();
        }
        if (Validators.isValidEmail(email) && Validators.isValidPassword(password) && Validators.isValidUsername(username)) {
            // generate a confirmation code and send it to the user's email, ask the user to enter the code in a  dialog box
            // if the code is correct, then write the user to the database
            // if the code is incorrect, then show an error message
            String code = UserService.generateConfirmationCode();
            // Utilize a Task to send the email in a separate thread
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    EmailService.sendEmail(email, "Confirmation Code", "Your confirmation code is " + code);
                    return null;
                }
            };
            // Show an alert while the email is being sent
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sending Email");
            alert.setHeaderText(null);
            alert.setContentText("Sending confirmation code to your email address...");
            task.setOnRunning(e -> { // show the alert when the email is being sent
                alert.show();
            });
            task.setOnSucceeded(e -> { // close the alert when the email is sent
                alert.hide();
            });
            // Start the task in a new thread
            new Thread(task).start();
            System.out.println(code);
            // Create a dialog box to ask the user to enter the confirmation code
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Confirmation Code");
            dialog.setHeaderText("Please enter the confirmation code sent to your email address.");
            dialog.setResizable(true);
            TextField confirmationCode = new TextField();
            confirmationCode.setPromptText("Confirmation Code");
            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(confirmationCode);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    return confirmationCode.getText();
                }
                return null;
            });
            // Show the dialog box and get the user's input
            String confirmationCodeInput = dialog.showAndWait().orElse("");
            if (!confirmationCodeInput.equals(code)) { // if the confirmation code is incorrect, show an error message
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("The confirmation code you entered is incorrect.");
                error.showAndWait();
                return;
            } else { // if the confirmation code is correct, write the user to the database
                UserService.writeUserToDatabase(username, email, password);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText(null);
                success.setContentText("Your account has been created successfully. Please proceed to the Login page to log in.");
                success.showAndWait();
            }
        }
    }
}
