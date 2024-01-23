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

        rightAnchorPane.getChildren().addAll(welcomeText, descriptionText, imageView);

        // Center AnchorPane
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefSize(630.0, 540.0);
        //centerAnchorPane.setStyle("-fx-background-color: #a2f5c0;");
        centerAnchorPane.getStyleClass().add("app-bg");

        Label loginLabel = new Label("Sign Up");
        loginLabel.setLayoutX(280.0);
        loginLabel.setLayoutY(72.0);
        loginLabel.setPrefHeight(65.0);
        loginLabel.setPrefWidth(150.0);
        loginLabel.setFont(new Font("Arial", 33.0));

        // y diff between text label and text field - 30px
        // y diff between text field and text label - 50px
        // y diff between text field and button - 50px

        Label usernameLabel = new Label("Username");
        usernameLabel.setLayoutX(220.0);
        usernameLabel.setLayoutY(150.0);
        usernameLabel.setFont(new Font(18.0));

        TextField usernameField = new TextField();
        usernameField.setLayoutX(220.0);
        usernameField.setLayoutY(180.0);
        usernameField.setPrefWidth(240.0);
        usernameField.setPromptText("Username");
        usernameField.setFont(new Font(15.0));
        usernameField.getStyleClass().add("textfield-design");

        Label emailTextLabel = new Label("Email Address");
        emailTextLabel.setLayoutX(220.0);
        emailTextLabel.setLayoutY(230.0);
        emailTextLabel.setFont(new Font(18.0));

        TextField emailTextField = new TextField();
        emailTextField.setLayoutX(220.0);
        emailTextField.setLayoutY(260.0);
        emailTextField.setPrefHeight(25.0);
        emailTextField.setPrefWidth(240.0);
        emailTextField.setPromptText("Email Address");
        emailTextField.setFont(new Font(15.0));
        emailTextField.getStyleClass().add("textfield-design");

        Label passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(220.0);
        passwordLabel.setLayoutY(310.0);
        passwordLabel.setFont(new Font(18.0));

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(220.0);
        passwordField.setLayoutY(340.0);
        passwordField.setPrefHeight(25.0);
        passwordField.setPrefWidth(240.0);
        passwordField.setPromptText("Password");
        passwordField.setFont(new Font(15.0));
        passwordField.getStyleClass().add("textfield-design");

//        ChoiceBox<String> accountTypeChoiceBox = new ChoiceBox<>();
//        accountTypeChoiceBox.setLayoutX(220.0);
//        accountTypeChoiceBox.setLayoutY(180.0);
//        accountTypeChoiceBox.setPrefWidth(240.0);
//        accountTypeChoiceBox.setItems(FXCollections.observableArrayList("Client", "Administrator"));

        Button loginButton = new Button("Back to Login");
        loginButton.setId("loginPage-login");
        loginButton.setLayoutX(220.0);
        loginButton.setLayoutY(390.0);
        loginButton.setPrefHeight(30.0);
        loginButton.setPrefWidth(100.0);
        loginButton.getStyleClass().add("button-design");
        loginButton.setOnAction(e -> stage.setScene(loginPage.createScene(stage, new signUpPage())));

        Button signUpButton = new Button("Sign Up");
        signUpButton.setId("loginPage-signup");
        signUpButton.setLayoutX(370.0);
        signUpButton.setLayoutY(390.0);
        signUpButton.setPrefHeight(30.0);
        signUpButton.setPrefWidth(90.0);
        signUpButton.getStyleClass().add("button-design");
        signUpButton.setOnAction(event -> {
            validate(usernameField.getText(), emailTextField.getText(),passwordField.getText());
            usernameField.clear();
            emailTextField.clear();
            passwordField.clear();
        });

        centerAnchorPane.getChildren().addAll(loginLabel, emailTextLabel, emailTextField, passwordLabel, passwordField,
                usernameField, usernameLabel, loginButton, signUpButton);

        // Set components in the BorderPane
        root.setRight(rightAnchorPane);
        root.setCenter(centerAnchorPane);

        Scene scene = new Scene(root, 960, 540);
        scene.getStylesheets().add(Objects.requireNonNull(compsciia.class.getResource("/com/example/compsciia/stylesheet.css")).toExternalForm());
        return scene;
    }

    private void validate(String username, String email, String password) {
        if (!Validators.isValidEmail(email)) {
            Validators.showInvalidEmailPopup();
        }
        if (!Validators.isValidPassword(password)) {
            Validators.showInvalidPasswordPopup();
        }
        if (!Validators.isValidUsername(username)){
            Validators.showInvalidUsernamePopup();
        }
        if (Validators.isValidEmail(email) && Validators.isValidPassword(password) && Validators.isValidUsername(username)) {
            // generate a confirmation code and send it to the user's email, ask the user to enter the code in a  dialog box
            // if the code is correct, then write the user to the database
            // if the code is incorrect, then show an error message
            String code = UserService.generateConfirmationCode();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    EmailService.sendEmail(email, "Confirmation Code", "Your confirmation code is " + code);
                    return null;
                }
            };
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sending Email");
            alert.setHeaderText(null);
            alert.setContentText("Sending confirmation code to your email address...");
            task.setOnRunning(e -> {
                alert.show();
            });
            task.setOnSucceeded(e -> {
                alert.hide();
            });
            new Thread(task).start();
            System.out.println(code);
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
            String confirmationCodeInput = dialog.showAndWait().orElse("");
            if (!confirmationCodeInput.equals(code)) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("The confirmation code you entered is incorrect.");
                error.showAndWait();
                return;
            } else {
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
