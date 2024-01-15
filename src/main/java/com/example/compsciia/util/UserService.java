package com.example.compsciia.util;
import com.example.compsciia.models.User;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class UserService {
    public static void writeUserToDatabase(String username, String email, String password){
        String query = "INSERT INTO app_users (email, password, username) VALUES (?, ?, ?)";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, username);
            stmt.executeUpdate();
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Username: " + username);
            System.out.println("User added to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUserFromDatabase(String email){
        String query = "DELETE FROM app_users WHERE email = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
            System.out.println("Email: " + email);
            System.out.println("User deleted from database");
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

    public static boolean isUsernameTaken(String username){
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
}
