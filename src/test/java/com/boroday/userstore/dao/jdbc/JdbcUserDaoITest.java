package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.util.TestDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {

    private static TestDataSource testDataSource = new TestDataSource();
    private static JdbcDataSource jdbcDataSource;

    @BeforeAll
    public static void createTestData() throws IOException, SQLException {
        jdbcDataSource = testDataSource.init();
    }

    @Test
    public void testGetAll() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        List<User> users = userDao.getAll();
        assertFalse(users.isEmpty());
        assertEquals(users.size(), 2);

        /*for (User user : users) {
            assertNotEquals(user.getId(), 0);
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
            assertNotEquals(user.getSalary(), 0.0);
            assertNotNull(user.getSalary());
        }*/
    }
}
