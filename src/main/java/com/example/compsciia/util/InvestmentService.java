package com.example.compsciia.util;

import com.example.compsciia.models.Investment;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ArrayList;
public class InvestmentService {

    public static void writeInvestmentToDatabase(int client_id, String investment_name, Double investment_amount, LocalDate investment_date, String investment_description){
        String query = "INSERT INTO investments (client_id, investment_name, investment_amount, investment_date, investment_description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, client_id);
            stmt.setString(2, investment_name);
            stmt.setDouble(3, investment_amount);
            stmt.setObject(4, investment_date);
            stmt.setString(5, investment_description);
            stmt.executeUpdate();
            System.out.println("Client ID: " + client_id);
            System.out.println("Investment Name: " + investment_name);
            System.out.println("Investment Amount: " + investment_amount);
            System.out.println("Investment Date: " + investment_date);
            System.out.println("Investment Description: " + investment_description);
            System.out.println("Investment added to database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Investment Added");
            alert.setHeaderText(null);
            alert.setContentText("New investment " + investment_name + " has been added to the database successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Investment getInvestmentFromDatabase(int investment_id){
        String query = "SELECT * FROM investments WHERE investment_id = ?";
        Investment investment = null;

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, investment_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                investment = Investment.fromResultSet(rs);
                System.out.println("Investment ID: " + investment.getInvestmentId());
                System.out.println("Client ID: " + investment.getClientId());
                System.out.println("Investment retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return investment;
    }

    public static void updateInvestmentInDatabase(Integer investmentId, String newInvestmentName, Double newInvestmentAmount, LocalDate newInvestmentDate, String newInvestmentDescription){
        String query = "UPDATE investments SET investment_name = ?, investment_amount = ?, investment_date = ?, investment_description = ? WHERE investment_id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setString(1, newInvestmentName);
            stmt.setDouble(2, newInvestmentAmount);
            stmt.setObject(3, newInvestmentDate);
            stmt.setString(4, newInvestmentDescription);
            stmt.setInt(5, investmentId);
            stmt.executeUpdate();
            System.out.println("Investment ID: " + investmentId);
            System.out.println("Investment Name: " + newInvestmentName);
            System.out.println("Investment Amount: " + newInvestmentAmount);
            System.out.println("Investment Date: " + newInvestmentDate);
            System.out.println("Investment Description: " + newInvestmentDescription);
            System.out.println("Investment updated in database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Investment Updated");
            alert.setHeaderText(null);
            alert.setContentText("Investment with ID " + investmentId + " has been updated in the database successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteInvestmentFromDatabase(Integer investmentId){
        String query = "DELETE FROM investments WHERE investment_id = ?";

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            stmt.setInt(1, investmentId);
            stmt.executeUpdate();
            System.out.println("ID: " + investmentId);
            System.out.println("Investment deleted from database");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Investment Deleted");
            alert.setHeaderText(null);
            alert.setContentText("Investment with ID " + investmentId + " has been deleted from the database successfully.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Investment> getAllInvestmentsFromDatabase(){
        String query = "SELECT * FROM investments";
        ArrayList<Investment> investments = new ArrayList<>();

        try (Connection conn = database.connect(); PreparedStatement stmt = Objects.requireNonNull(conn).prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Investment investment = Investment.fromResultSet(rs);
                investments.add(investment);
                System.out.println("Client with ID " + investment.getInvestmentId() + " retrieved from database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return investments;
    }
}
