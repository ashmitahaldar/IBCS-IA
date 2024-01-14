package com.example.compsciia.util;
import com.example.compsciia.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class userService {
    public static void writeUserToDatabase(String email, String password){
        String query = "INSERT INTO app_users (email, password) VALUES (?, ?)";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
