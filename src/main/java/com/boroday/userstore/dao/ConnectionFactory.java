package com.boroday.userstore.dao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ConnectionFactory {
    public static Connection getConnection() {
        String host;
        String username;
        String password;

        try {
            List<String> lines = Files.readAllLines(Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "applicationProperties"), StandardCharsets.UTF_8);
            host = lines.get(2);
            username = lines.get(3);
            password = lines.get(4);
        } catch (IOException ex) {
            throw new RuntimeException("ApplicationProperties file could not be read", ex);
        }
        try {
            return DriverManager.getConnection(host, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException("Cannot connect to database. Please check your path, username and password", ex);
        }
    }
}
