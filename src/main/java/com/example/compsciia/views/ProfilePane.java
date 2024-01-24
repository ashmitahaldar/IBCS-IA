package com.example.compsciia.views;
import com.example.compsciia.compsciia;

import com.example.compsciia.models.User;
import com.example.compsciia.util.ClientService;
import com.example.compsciia.util.UserService;
import com.example.compsciia.util.Validators;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ProfilePane {
    public static AnchorPane createPane(Integer userId, Stage stage) {
        AtomicReference<User> user = new AtomicReference<>(getCurrentUser(userId));
        final FileChooser fileChooser = new FileChooser();

        AnchorPane root = new AnchorPane();
        root.setLayoutX(10.0);
        root.setLayoutY(10.0);
        root.setPrefHeight(600.0);
        root.setPrefWidth(750.0);
        root.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(600.0);
        borderPane.setPrefWidth(750.0);

        // Top AnchorPane
        AnchorPane topAnchorPane = new AnchorPane();
        topAnchorPane.setPrefHeight(100.0);
        topAnchorPane.setPrefWidth(750.0);
        topAnchorPane.getStyleClass().add("app-bg");
        topAnchorPane.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Label in Top AnchorPane
        Label titleLabel = new Label("User Profile");
        titleLabel.setLayoutX(20.0);
        titleLabel.setLayoutY(25.0);
        titleLabel.setPrefHeight(40.0);
        titleLabel.setPrefWidth(500.0);
        titleLabel.setFont(new javafx.scene.text.Font(40.0));
        topAnchorPane.getChildren().add(titleLabel);

        // Center AnchorPane
        AnchorPane centerAnchorPane = new AnchorPane();
        centerAnchorPane.setPrefHeight(500.0);
        centerAnchorPane.setPrefWidth(750.0);

        // Labels
        Label labelUsername = new Label("Username");
        Label labelFirstName = new Label("First Name");
        Label labelLastName = new Label("Last Name");
        Label labelEmailAddress = new Label("Email Address");
        Label labelPassword = new Label("Password");
        Label labelPhoneNumber = new Label("Phone Number");
        Label labelDateOfBirth = new Label("Date of Birth");
        Label labelClientsManaged = new Label("Clients Managed: " + ClientService.getNumberOfClientsFromDatabaseForUser(userId));

        // TextFields
        TextField textFieldUsername = new TextField();
        TextField textFieldFirstName = new TextField();
        TextField textFieldLastName = new TextField();
        TextField textFieldEmailAddress = new TextField();
        TextField textFieldPassword = new TextField();
        TextField textFieldPhoneNumber = new TextField();
        DatePicker fieldDateOfBirth = new DatePicker();

        // Setting properties for labels
        setLabelProperties(labelUsername, 30.0, 40.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelFirstName, 30.0, 85.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelLastName, 30.0, 130.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelEmailAddress, 30.0, 175.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelPassword, 30.0, 220.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelPhoneNumber, 30.0, 285.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelDateOfBirth, 30.0, 330.0, 40.0, 150.0, 20.0);
        setLabelProperties(labelClientsManaged, 450.0, 330.0, 40.0, 200.0, 20.0);

        // Setting properties for TextFields
        setTextFieldProperties(textFieldUsername, user.get().getUsername(), 180.0, 40.0, 40.0, 250.0);
        setTextFieldProperties(textFieldFirstName, user.get().getFirstName(), 180.0, 85.0, 40.0, 250.0);
        setTextFieldProperties(textFieldLastName, user.get().getLastName(), 180.0, 130.0, 40.0, 250.0);
        setTextFieldProperties(textFieldEmailAddress, user.get().getEmail(), 180.0, 175.0, 40.0, 250.0);
        setTextFieldProperties(textFieldPassword, user.get().getPassword(), 180.0, 220.0, 40.0, 250.0);
        setTextFieldProperties(textFieldPhoneNumber, user.get().getPhoneNumber(), 180.0, 285.0, 40.0, 250.0);
        fieldDateOfBirth.setLayoutX(180);
        fieldDateOfBirth.setLayoutY(330);
        fieldDateOfBirth.setPrefHeight(40);
        fieldDateOfBirth.setPrefWidth(250);
        fieldDateOfBirth.setValue(user.get().getDateOfBirth());
        fieldDateOfBirth.getStyleClass().add("textfield-design");
        fieldDateOfBirth.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());

        // Adding labels and TextFields to centerAnchorPane
        centerAnchorPane.getChildren().addAll(
                labelUsername, labelFirstName, labelLastName, labelEmailAddress,
                labelPassword, labelPhoneNumber, labelDateOfBirth, labelClientsManaged,
                textFieldUsername, textFieldFirstName, textFieldLastName, textFieldEmailAddress,
                textFieldPassword, textFieldPhoneNumber, fieldDateOfBirth
        );

        // Circle in Center AnchorPane
        Circle profilePicture = new Circle(600.0, 150.0, 120.0, Color.DODGERBLUE);
        Image im = new Image(Objects.requireNonNull(compsciia.class.getResource("defaultImage.jpg")).toExternalForm(),false);
        Task<Image> getUserProfilePictureTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return user.get().getProfileImage();
            }
        };
        getUserProfilePictureTask.setOnSucceeded(e -> {
            if (getUserProfilePictureTask.getValue() != null) {
                profilePicture.setFill(new ImagePattern(getUserProfilePictureTask.getValue()));
            } else {
                profilePicture.setFill(new ImagePattern(im));
            }
        });
        new Thread(getUserProfilePictureTask).start();
        centerAnchorPane.getChildren().add(profilePicture);

        // Buttons in Center AnchorPane
        Button uploadImageButton = new Button("Upload Image");
        uploadImageButton.setLayoutX(550.0);
        uploadImageButton.setLayoutY(280.0);
        uploadImageButton.setMnemonicParsing(false);
        uploadImageButton.setPrefHeight(30.0);
        uploadImageButton.setPrefWidth(100.0);
        uploadImageButton.getStyleClass().add("button-design");
        uploadImageButton.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        uploadImageButton.setOnAction(e -> {
            fileChooser.setTitle("Upload Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File imageFile = fileChooser.showOpenDialog(null);
            if (imageFile != null) {
                String imageFilePath = imageFile.toURI().toString();
                UserService.updateUserImageInDatabase(userId, imageFile);
                profilePicture.setFill(new javafx.scene.paint.ImagePattern(new Image(imageFilePath)));
                profilePaneTextFieldUpdate(userId, user, textFieldUsername, textFieldFirstName, textFieldLastName, textFieldEmailAddress, textFieldPassword, textFieldPhoneNumber, fieldDateOfBirth);
            }
        });

        Button saveChangesButton = new Button("Save Changes");
        saveChangesButton.setLayoutX(30.0);
        saveChangesButton.setLayoutY(420.0);
        saveChangesButton.setMnemonicParsing(false);
        saveChangesButton.setPrefHeight(40.0);
        saveChangesButton.setPrefWidth(100.0);
        saveChangesButton.getStyleClass().add("button-design");
        saveChangesButton.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        saveChangesButton.setOnAction(event -> {
            // add validation
            String username = textFieldUsername.getText();
            String firstName = textFieldFirstName.getText();
            String lastName = textFieldLastName.getText();
            String email = textFieldEmailAddress.getText();
            String password = textFieldPassword.getText();
            String phoneNumber = textFieldPhoneNumber.getText();
            LocalDate dateOfBirth = fieldDateOfBirth.getValue();
            isValid(userId, email, password, username, firstName, lastName, phoneNumber, dateOfBirth);
            profilePaneTextFieldUpdate(userId, user, textFieldUsername, textFieldFirstName, textFieldLastName, textFieldEmailAddress, textFieldPassword, textFieldPhoneNumber, fieldDateOfBirth);
        });

        Button deleteAccountButton = new Button("Delete Account");
        deleteAccountButton.setLayoutX(600.0);
        deleteAccountButton.setLayoutY(420.0);
        deleteAccountButton.setMnemonicParsing(false);
        deleteAccountButton.setPrefHeight(40.0);
        deleteAccountButton.getStyleClass().add("button-design");
        deleteAccountButton.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        deleteAccountButton.setOnAction(e -> deleteUserAccount(userId, stage));

        Button clearAccountDataButton = new Button("Clear Account Data");
        clearAccountDataButton.setLayoutX(450.0);
        clearAccountDataButton.setLayoutY(420.0);
        clearAccountDataButton.setMnemonicParsing(false);
        clearAccountDataButton.setPrefHeight(40.0);
        clearAccountDataButton.getStyleClass().add("button-design");
        clearAccountDataButton.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        clearAccountDataButton.setOnAction(e -> deleteUserAccountData(userId));

        Button revertEditsButton = new Button("Revert Edits");
        revertEditsButton.setLayoutX(180.0);
        revertEditsButton.setLayoutY(420.0);
        revertEditsButton.setMnemonicParsing(false);
        revertEditsButton.setPrefHeight(40.0);
        revertEditsButton.setPrefWidth(100.0);
        revertEditsButton.getStyleClass().add("button-design");
        revertEditsButton.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
        revertEditsButton.setOnAction(e -> {
            profilePaneTextFieldUpdate(userId, user, textFieldUsername, textFieldFirstName, textFieldLastName, textFieldEmailAddress, textFieldPassword, textFieldPhoneNumber, fieldDateOfBirth);
        });

        centerAnchorPane.getChildren().addAll(uploadImageButton, saveChangesButton, deleteAccountButton, clearAccountDataButton, revertEditsButton);

        // Adding Top and Center AnchorPanes to BorderPane
        borderPane.setTop(topAnchorPane);
        borderPane.setCenter(centerAnchorPane);

        // Adding BorderPane to AnchorPane
        root.getChildren().add(borderPane);

        return root;
    }
    private static void profilePaneTextFieldUpdate(Integer userId, AtomicReference<User> user, TextField textFieldUsername, TextField textFieldFirstName, TextField textFieldLastName, TextField textFieldEmailAddress, TextField textFieldPassword, TextField textFieldPhoneNumber, DatePicker fieldDateOfBirth) {
        user.set(getCurrentUser(userId));
        textFieldUsername.setText(user.get().getUsername());
        textFieldFirstName.setText(user.get().getFirstName());
        textFieldLastName.setText(user.get().getLastName());
        textFieldEmailAddress.setText(user.get().getEmail());
        textFieldPassword.setText(user.get().getPassword());
        textFieldPhoneNumber.setText(user.get().getPhoneNumber());
        fieldDateOfBirth.setValue(user.get().getDateOfBirth());
    }

    private static void setLabelProperties(Label label, double layoutX, double layoutY, double prefHeight, double prefWidth, double fontSize) {
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefHeight(prefHeight);
        label.setPrefWidth(prefWidth);
        label.setFont(new javafx.scene.text.Font(fontSize));
    }

    private static void setTextFieldProperties(TextField textField, String value, double layoutX, double layoutY, double prefHeight, double prefWidth) {
        textField.setLayoutX(layoutX);
        textField.setLayoutY(layoutY);
        textField.setPrefHeight(prefHeight);
        textField.setPrefWidth(prefWidth);
        textField.setText(value);
        textField.setFont(new Font(18.0));
        textField.getStyleClass().add("textfield-design");
        textField.getStylesheets().add(compsciia.class.getResource("stylesheet.css").toExternalForm());
    }

    private static User getCurrentUser(Integer userId){
        return UserService.getUserFromDatabase(userId);
    }

    private static void isValid(Integer userId, String email, String password, String username, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth) {
        if (Validators.isValidFirstName(firstName) && Validators.isValidLastName(lastName) && Validators.isValidEmail(email) && Validators.isValidPassword(password) && isValidUsernameProfilePane(userId, username) && Validators.isValidDateOfBirth(dateOfBirth)){
            // update user
            UserService.updateUserInDatabase(userId, email, password, username, firstName, lastName, phoneNumber, dateOfBirth);

        } else {
            if (!Validators.isValidFirstName(firstName)) {
                Validators.showInvalidFirstNamePopup();
            }
            if (!Validators.isValidLastName(lastName)) {
                Validators.showInvalidLastNamePopup();
            }
            if (!Validators.isValidEmail(email)) {
                Validators.showInvalidEmailPopup();
            }
            if (!Validators.isValidPassword(password)) {
                Validators.showInvalidPasswordPopup();
            }
            if (!isValidUsernameProfilePane(userId, username)) {
                Validators.showInvalidUsernamePopup();
            }
            if (!Validators.isValidPhoneNumber(phoneNumber)) {
                Validators.showInvalidPhoneNumberPopup();
            }
            if (!Validators.isValidDateOfBirth(dateOfBirth)) {
                Validators.showInvalidDateOfBirthPopup();
            }
        }
    }

    private static boolean isValidUsernameProfilePane(Integer userId, String newUsername){
        String oldUsername = UserService.getUserFromDatabase(userId).getUsername();
        if (newUsername.equals(oldUsername)){
            return true;
        } else return (newUsername.matches("[a-zA-Z0-9]+") && !UserService.checkIfUsernameIsTaken(newUsername));
    }
    private static void deleteUserAccountData(Integer userId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Account Data");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to clear all your account data?");
        ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (buttonType.equals(ButtonType.OK)) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Clear Account Data");
            dialog.setHeaderText("Please enter your password to confirm clearing of your account data:");
            dialog.setResizable(true);
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefSize(200, 100);
            passwordField.setPromptText("Password");
            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(passwordField);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    return passwordField.getText();
                }
                return null;
            });
            dialog.showAndWait().ifPresent(password -> {
                if (password.equals(UserService.getUserFromDatabase(userId).getPassword())) {
                    UserService.clearUserAccountData(userId);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Account Data Cleared");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Your account data has been cleared successfully.");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Incorrect Password");
                    alert1.setHeaderText(null);
                    alert1.setContentText("The password you entered is incorrect.");
                    alert1.showAndWait();
                }
            });
        }
    }
    private static void deleteUserAccount(Integer userId, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete your account?");
        ButtonType buttonType = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (buttonType.equals(ButtonType.OK)) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Delete Account");
            dialog.setHeaderText("Please enter your password to confirm deletion of your account:");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(300, 200);
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(passwordField);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    return passwordField.getText();
                }
                return null;
            });
            dialog.showAndWait().ifPresent(password -> {
                if (password.equals(UserService.getUserFromDatabase(userId).getPassword())) {
                    UserService.deleteUserFromDatabase(userId);
                    loginPage loginpage = new loginPage();
                    stage.setScene(loginpage.createScene(stage, new signUpPage()));
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Account Deleted");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Your account has been deleted successfully.");
                    alert1.showAndWait();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Incorrect Password");
                    alert1.setHeaderText(null);
                    alert1.setContentText("The password you entered is incorrect.");
                    alert1.showAndWait();
                }
            });
        }
    }
}
