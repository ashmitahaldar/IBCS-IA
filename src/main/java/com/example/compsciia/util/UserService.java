package com.example.compsciia.util;
import com.example.compsciia.compsciia;
import com.example.compsciia.models.User;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
//import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.security.SecureRandom;

public class UserService {
    public static void writeUserToDatabase(String username, String email, String password){
        String query = "INSERT INTO app_users (email, password, username, profile_image) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);
            BufferedImage bufferedImage = ImageIO.read(new File(compsciia.class.getResource("defaultImage.jpg").toURI()));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBinaryStream(4, (InputStream) fis, os.toByteArray().length);
            stmt.executeUpdate();
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Username: " + username);
            System.out.println("User added to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUserFromDatabase(int user_id){
        String query = "DELETE FROM app_users WHERE id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
            System.out.println("ID: " + user_id);
            System.out.println("User deleted from database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Deleted");
            alert.setHeaderText(null);
            alert.setContentText("User with ID " + user_id + " has been deleted from the database successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUserInDatabase(Integer userId, String newEmail, String newPassword, String newUsername, String newFirstName, String newLastName, String newPhoneNumber, LocalDate newDateOfBirth){
        String query = "UPDATE app_users SET email = ?, password = ?, username = ?, first_name = ?, last_name = ?, phone_number = ?, date_of_birth = ? WHERE id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, newPassword);
            stmt.setString(3, newUsername);
            stmt.setString(4, newFirstName);
            stmt.setString(5, newLastName);
            stmt.setString(6, newPhoneNumber);
            stmt.setObject(7, newDateOfBirth);
            stmt.setInt(8, userId);
            stmt.executeUpdate();
            System.out.println("ID: " + userId);
            System.out.println("Email: " + newEmail);
            System.out.println("Password: " + newPassword);
            System.out.println("Username: " + newUsername);
            System.out.println("First Name: " + newFirstName);
            System.out.println("Last Name: " + newLastName);
            System.out.println("Phone Number: " + newPhoneNumber);
            System.out.println("Date of Birth: " + newDateOfBirth.toString());
            System.out.println("User updated in database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Details Updated");
            alert.setHeaderText(null);
            alert.setContentText("Your user details have been updated successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User getUserFromDatabase(Integer userId){
        User user = null;
        String query = "SELECT * FROM app_users WHERE id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                user = User.fromResultSet(resultset);
                System.out.println("ID: " + userId);
                System.out.println("User retrieved from database");
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static User getUserFromDatabase(String email, String password){
        User user = null;
        String query = "SELECT * FROM app_users WHERE email = ? AND password = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                user = User.fromResultSet(resultset);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("User retrieved from database");
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static ArrayList<User> getAllUsersFromDatabase(){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM app_users";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                User user = User.fromResultSet(resultset);
                users.add(user);
                System.out.println("User retrieved from database");
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static void clearUserAccountData(int user_id){
        ArrayList<Integer> clientIds = new ArrayList<>(ClientService.getAllClientIDsFromDatabaseForUser(user_id));
        for (Integer clientId : clientIds) {
            InvestmentService.clearAllInvestmentsFromDatabaseForClient(clientId);
            ClientService.deleteClientFromDatabase(clientId);
        }
        System.out.println("All client data cleared from database");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Data Cleared");
        alert.setHeaderText(null);
        alert.setContentText("All client data and associated investment data for the user has been cleared from the database successfully.");
        alert.showAndWait();
    }

    public static void updateUserImageInDatabase(Integer userId, File imageFile){
        String query = "UPDATE app_users SET profile_image = ? WHERE id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            FileInputStream fis = new FileInputStream(imageFile);
            stmt.setBinaryStream(1, fis, imageFile.length());
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("ID: " + userId);
            System.out.println("Profile Image: " + imageFile);
            System.out.println("User image updated in database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Profile Image Updated");
            alert.setHeaderText(null);
            alert.setContentText("Your user profile image has been updated successfully.");
            alert.showAndWait();
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkIfUsernameIsTaken(String username){
        String query = "SELECT * FROM app_users WHERE username = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                System.out.println("Username: " + username);
                System.out.println("Username is taken");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static BufferedImage convertToBufferedImage(Image image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static Boolean checkIfUserExists(String email){
        String query = "SELECT * FROM app_users WHERE email = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                System.out.println("Email: " + email);
                System.out.println("User exists");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void forgotPassword(String email){
        String query = "SELECT * FROM app_users WHERE email = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet resultset = stmt.executeQuery();
            while (resultset.next()) {
                System.out.println("Email: " + email);
                System.out.println("User exists");
                String password = resultset.getString("password");
                String username = resultset.getString("username");
                String firstName = resultset.getString("first_name");
                String lastName = resultset.getString("last_name");
                String phoneNumber = resultset.getString("phone_number");
                LocalDate dateOfBirth = resultset.getObject("date_of_birth", LocalDate.class);
//                String message = "Hi " + firstName + " " + lastName + ",\n\nYour password is: " + password + "\n\nKind regards,\nClientify";
//                EmailService.sendEmail(email, "Clientify - Forgot Password", message);
//                System.out.println(message);
                String code = generateConfirmationCode();
                String message = "Hi " + firstName + " " + lastName + ",\n\nYour confirmation code is: " + code + "\n\nKind regards,\nClientify";
//                EmailService.sendEmail(email, "Clientify - Forgot Password", message);
                Task<Void> sendForgotPasswordConfirmationCodeTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        EmailService.sendEmail(email, "Clientify - Forgot Password", message);
                        return null;
                    }
                };
                Alert forgotPasswordEmailRunningAlert = new Alert(Alert.AlertType.INFORMATION);
                forgotPasswordEmailRunningAlert.setTitle("Sending Email");
                forgotPasswordEmailRunningAlert.setHeaderText(null);
                forgotPasswordEmailRunningAlert.setContentText("Sending email...");
                sendForgotPasswordConfirmationCodeTask.setOnRunning((e) -> {
                    forgotPasswordEmailRunningAlert.show();
                });
                sendForgotPasswordConfirmationCodeTask.setOnSucceeded(e -> {
                    forgotPasswordEmailRunningAlert.hide();
                });
                new Thread(sendForgotPasswordConfirmationCodeTask).start();

                System.out.println("Email sent");

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Confirmation Code");
                dialog.setHeaderText("Please enter the confirmation code sent to your email address.");
                dialog.setResizable(true);
                javafx.scene.control.TextField confirmationCode = new TextField();
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
                dialog.showAndWait();
                String confirmationCodeInput = dialog.showAndWait().orElse("");
                if (!confirmationCodeInput.equals(code)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText(null);
                    error.setContentText("The confirmation code you entered is incorrect.");
                    error.showAndWait();
                    return;
                } else {
                    Dialog<String> dialog2 = new Dialog<>();
                    dialog2.setTitle("New Password");
                    dialog2.setHeaderText("Please enter your new password.");
                    dialog2.setResizable(true);
                    javafx.scene.control.TextField newPassword = new TextField();
                    newPassword.setPromptText("New Password");
                    ButtonType confirmButtonType2 = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                    dialog2.getDialogPane().getButtonTypes().addAll(confirmButtonType2, ButtonType.CANCEL);
                    dialog2.getDialogPane().setContent(newPassword);
                    dialog2.setResultConverter(dialogButton -> {
                        if (dialogButton == confirmButtonType2) {
                            return newPassword.getText();
                        }
                        return null;
                    });
                    dialog2.showAndWait();
                    String newPasswordInput = dialog2.showAndWait().orElse("");
                    if (newPasswordInput.equals("")) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText(null);
                        error.setContentText("You must enter a new password.");
                        error.showAndWait();
                        return;
                    } else if (!Validators.isValidPassword(newPasswordInput)) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText(null);
                        error.setContentText("Your password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number and one special character.");
                        error.showAndWait();
                        return;
                    } else if (newPasswordInput.equals(password)) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText(null);
                        error.setContentText("Your new password cannot be the same as your old password.");
                        error.showAndWait();
                        return;
                    } else {
                        String query2 = "UPDATE app_users SET password = ? WHERE email = ?";
                        try (Connection conn2 = database.connect(); PreparedStatement stmt2 = Objects.requireNonNull(conn2).prepareStatement(query2)) {
                            stmt2.setString(1, newPasswordInput);
                            stmt2.setString(2, email);
                            stmt2.executeUpdate();
                            System.out.println("Email: " + email);
                            System.out.println("Password: " + newPasswordInput);
                            System.out.println("User updated in database");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Password Updated");
                            alert.setHeaderText(null);
                            alert.setContentText("Your password has been updated successfully. Please login again");
                            alert.showAndWait();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String generateConfirmationCode(){
        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom secureRandom = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int randomIndex = secureRandom.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
