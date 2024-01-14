package com.example.compsciia.util;
import java.sql.*;

public class database {

    public database() throws SQLException {
    }

    public static Connection connect(){
        Connection conn;
        try {
            // db parameters
            String url = "jdbc:postgresql://localhost:5432/compsciia";
            // create a connection to the database
            conn = DriverManager.getConnection(url, "postgres", "postgres");
            System.out.println("Connection to PostgreSQL has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
