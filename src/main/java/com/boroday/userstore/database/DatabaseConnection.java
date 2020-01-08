package com.boroday.userstore.database;

import java.sql.*;

public class DatabaseConnection {

    private final String HOST = "jdbc:postgresql://localhost:5432/userstore";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "12345";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
