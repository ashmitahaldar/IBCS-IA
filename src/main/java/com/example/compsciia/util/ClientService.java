package com.example.compsciia.util;

import com.example.compsciia.models.Client;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ArrayList;

public class ClientService {
    public static void writeClientToDatabase(int user_id, String client_first_name, String client_last_name, String client_email, String client_phone_number, LocalDate client_date_of_birth, String client_address, String client_registered_id){
        String query = "INSERT INTO clients (user_id, client_first_name, client_last_name, client_email, client_phone_number, client_date_of_birth, client_address, client_registered_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            stmt.setString(2, client_first_name);
            stmt.setString(3, client_last_name);
            stmt.setString(4, client_email);
            stmt.setString(5, client_phone_number);
            stmt.setObject(6, client_date_of_birth);
            stmt.setString(7, client_address);
            stmt.setString(8, client_registered_id);
            stmt.executeUpdate();
            System.out.println("User ID: " + user_id);
            System.out.println("Client First Name: " + client_first_name);
            System.out.println("Client Last Name: " + client_last_name);
            System.out.println("Client Email: " + client_email);
            System.out.println("Client Phone Number: " + client_phone_number);
            System.out.println("Client Date of Birth: " + client_date_of_birth);
            System.out.println("Client Address: " + client_address);
            System.out.println("Client Registered ID: " + client_registered_id);
            System.out.println("Client added to database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Client Added");
            alert.setHeaderText(null);
            alert.setContentText("New client " + client_first_name + " " + client_last_name + " has been added to the database successfully. Please reload the page for changes to be reflected.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Client getClientFromDatabase(int client_id){
        String query = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, client_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                client = Client.fromResultSet(rs);
                System.out.println("Client ID: " + client.getClientId());
                System.out.println("User ID: " + client.getUserId());
                System.out.println("Client retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public static Client getClientFromDatabase(String client_first_name, String client_last_name){
        String query = "SELECT * FROM clients WHERE client_first_name = ? AND client_last_name = ?";
        Client client = null;

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, client_first_name);
            stmt.setString(2, client_last_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                client = Client.fromResultSet(rs);
                System.out.println("Client ID: " + client.getClientId());
                System.out.println("User ID: " + client.getUserId());
                System.out.println("Client retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public static void updateClientInDatabase(int client_id, String client_first_name, String client_last_name, String client_email, String client_phone_number, LocalDate client_date_of_birth, String client_address, String client_registered_id){
        String query = "UPDATE clients SET client_first_name = ?, client_last_name = ?, client_email = ?, client_phone_number = ?, client_date_of_birth = ?, client_address = ?, client_registered_id = ? WHERE client_id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, client_first_name);
            stmt.setString(2, client_last_name);
            stmt.setString(3, client_email);
            stmt.setString(4, client_phone_number);
            stmt.setObject(5, client_date_of_birth);
            stmt.setString(6, client_address);
            stmt.setString(7, client_registered_id);
            stmt.setInt(8, client_id);
            stmt.executeUpdate();
            System.out.println("Client ID: " + client_id);
            System.out.println("User ID: " + getClientFromDatabase(client_id).getUserId());
            System.out.println("Client First Name: " + client_first_name);
            System.out.println("Client Last Name: " + client_last_name);
            System.out.println("Client Email: " + client_email);
            System.out.println("Client Phone Number: " + client_phone_number);
            System.out.println("Client Date of Birth: " + client_date_of_birth);
            System.out.println("Client Address: " + client_address);
            System.out.println("Client Registered ID: " + client_registered_id);
            System.out.println("Client updated in database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Client Details Updated");
            alert.setHeaderText(null);
            alert.setContentText("Client details have been updated successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

public static void deleteClientFromDatabase(int client_id){
        String query = "DELETE FROM clients WHERE client_id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, client_id);
            stmt.executeUpdate();
            System.out.println("Client ID: " + client_id);
            System.out.println("Client deleted from database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Client Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Client has been deleted successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Client> getAllClientsFromDatabase(){
        String query = "SELECT * FROM clients";
        ArrayList<Client> clients = new ArrayList<>();

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = Client.fromResultSet(rs);
                clients.add(client);
                System.out.println("Client with ID " + client.getClientId() + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    public static ArrayList<Client> getAllClientsFromDatabaseForUser(int user_id){
        String query = "SELECT * FROM clients WHERE user_id = ?";
        ArrayList<Client> clients = new ArrayList<>();

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = Client.fromResultSet(rs);
                clients.add(client);
                System.out.println("Client with ID " + client.getClientId() + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    public static ArrayList<String> getAllClientNamesFromDatabaseForUser(int user_id){
        String query = "SELECT client_first_name, client_last_name FROM clients WHERE user_id = ?";
        ArrayList<String> clientNames = new ArrayList<>();

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String clientName = rs.getString("client_first_name") + " " + rs.getString("client_last_name");
                clientNames.add(clientName);
                System.out.println("Client with name " + clientName + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clientNames;
    }

    public static ArrayList<Integer> getAllClientIDsFromDatabaseForUser(int user_id){
        String query = "SELECT client_id FROM clients WHERE user_id = ?";
        ArrayList<Integer> clientIDs = new ArrayList<>();

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int clientID = rs.getInt("client_id");
                clientIDs.add(clientID);
                System.out.println("Client with ID " + clientID + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clientIDs;
    }

    public static Integer getNumberOfClientsFromDatabaseForUser(int user_id){
        String query = "SELECT COUNT(*) FROM clients WHERE user_id = ?";
        int totalNumberOfClients = 0;

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalNumberOfClients = rs.getInt(1);
                System.out.println("Number of clients for user with ID " + user_id + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalNumberOfClients;
    }

}
