package com.example.compsciia.util;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public static boolean isValidUser(String email, String password) {
        return UserService.getUserFromDatabase(email, password) != null;
    }
    public static void showInvalidUserPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid User");
        alert.setHeaderText(null);
        alert.setContentText("A user with the entered credentials does not exist.");

        alert.showAndWait();
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void showInvalidEmailPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Email");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid email address.");
        alert.showAndWait();
    }
    public static boolean isValidPassword(String password) {
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

    public static void showInvalidPasswordPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Password");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a password with at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.");

        alert.showAndWait();
    }

    public static boolean isValidFirstName(String firstName) {
        return firstName.matches("[a-zA-Z]+");
    }

    public static boolean isValidLastName(String lastName) {
        return lastName.matches("[a-zA-Z]+");
    }

    public static boolean isValidUsername(String username) {
        if (username.matches("[a-zA-Z0-9]+") && !UserService.checkIfUsernameIsTaken(username)){ // ADD CHECKING TO SEE IF USERNAME EXISTS IN DB
            return true;
        } else return false;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[0-9]{10}");
    }

    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        return dateOfBirth.isBefore(LocalDate.now());
    }

    public static void showInvalidFirstNamePopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid First Name");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid first name.");
        alert.showAndWait();
    }

    public static void showInvalidLastNamePopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Last Name");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid last name.");
        alert.showAndWait();
    }

    public static void showInvalidUsernamePopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Username");
        alert.setHeaderText(null);
        alert.setContentText("Username invalid/already exists in database. Please enter a different username.");
        alert.showAndWait();
    }

    public static void showInvalidPhoneNumberPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Phone Number");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid phone number.");
        alert.showAndWait();
    }

    public static void showInvalidDateOfBirthPopup() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Date of Birth");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid date of birth.");
        alert.showAndWait();
    }
}
