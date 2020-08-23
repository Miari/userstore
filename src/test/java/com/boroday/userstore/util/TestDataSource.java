package com.boroday.userstore.util;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class TestDataSource {

    JdbcDataSource jdbcDataSource;
    Flyway flyway;

    public TestDataSource() {
        jdbcDataSource = new JdbcDataSource();
        //jdbcDataSource.setURL("jdbc:h2:mem:userstore;MODE=MySQL;DB_CLOSE_DELAY=-1");
        jdbcDataSource.setURL("jdbc:h2:mem:userstore;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        //jdbcDataSource.setUser("root");
        jdbcDataSource.setUser("postgres");
        jdbcDataSource.setPassword("root");
        flyway = Flyway.configure().dataSource(jdbcDataSource).load();
    }

    public JdbcDataSource init() {
        flyway.migrate();
        return jdbcDataSource;
    }

    public void cleanup() {
        flyway.clean();
    }
}
