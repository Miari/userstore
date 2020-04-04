package com.boroday.userstore.util;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDataSource {
    Connection connection;

    public JdbcDataSource init() throws SQLException, IOException {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:userstore;MODE=MySQL");
        jdbcDataSource.setUser("root");
        jdbcDataSource.setPassword("12345");
        connection = jdbcDataSource.getConnection();
        runScriptFromFile("db/migration/V1_0__initial_schema.sql");
        runScriptFromFile("db/migration/V2_0__insert_initial_users.sql");
        return jdbcDataSource;
    }

    private void runScriptFromFile(String path) throws IOException, SQLException {
        try (FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(path).getFile())) {
            RunScript.execute(connection, fileReader);
        }
    }

    public void cleanup() throws IOException, SQLException {
        runScriptFromFile("db/cleanup/cleanup.sql");
    }
}
