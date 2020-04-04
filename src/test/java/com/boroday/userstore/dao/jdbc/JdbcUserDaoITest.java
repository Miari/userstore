package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.util.TestDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {

    private static TestDataSource testDataSource = new TestDataSource();
    private static JdbcDataSource jdbcDataSource;
    private static List<User> expectedUsers;
    private User secondUser;

    @BeforeEach
    public void createUsersList() throws IOException, SQLException {
        jdbcDataSource = testDataSource.init();
        User firstUser = new User();
        firstUser.setId(1);
        firstUser.setFirstName("Amadina");
        firstUser.setLastName("Troll");
        firstUser.setSalary(1500.00);
        LocalDate localDateFirst = LocalDate.of(1993, 05, 13);
        firstUser.setDateOfBirth(localDateFirst);

        secondUser = new User();
        secondUser.setId(2);
        secondUser.setFirstName("Pavel");
        secondUser.setLastName("Litvak");
        secondUser.setSalary(1450.00);
        LocalDate localDateSecond = LocalDate.of(1977, 8, 3);
        secondUser.setDateOfBirth(localDateSecond);

        expectedUsers = new LinkedList<>();
        expectedUsers.add(firstUser);
        expectedUsers.add(secondUser);
    }

    @AfterEach
    public void removeUsers() throws IOException, SQLException {
        testDataSource.cleanup();
    }

    @Test
    public void testGetAll() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        List<User> users = userDao.getAll();
        assertEquals(users.size(), 2);
        for (User user : users) {
            expectedUsers.removeIf(expectedUser -> expectedUser.equals(user));
        }
        assertEquals(0, expectedUsers.size());
    }

    @Test
    public void testAddNewUser() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        User newUser = new User();
        newUser.setId(3);
        newUser.setFirstName("Evelina");
        newUser.setLastName("Rosdorf");
        newUser.setSalary(1300.00);
        LocalDate localDateSecond = LocalDate.of(1980, 7, 24);
        newUser.setDateOfBirth(localDateSecond);
        userDao.addNewUser(newUser);
        assertEquals(newUser, userDao.getUserById(3));
    }

    @Test
    public void testRemoveUser() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        User savedUser = userDao.getUserById(2);
        userDao.removeUser(2);
        List<User> users = userDao.getAll();
        assertEquals(1, users.size());
        assertNotEquals(users.get(0), savedUser);
    }

    @Test
    public void testGetUserById() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        assertEquals(secondUser, userDao.getUserById(2));
    }

    @Test
    public void testUpdateUser() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setFirstName("Mary");
        updatedUser.setLastName("Hops");
        updatedUser.setSalary(1600.00);
        LocalDate localDateSecond = LocalDate.of(1990, 2, 12);
        updatedUser.setDateOfBirth(localDateSecond);
        userDao.updateUser(updatedUser);
        assertEquals(updatedUser, userDao.getUserById(1));
    }

    @Test
    public void testSearchUser() {
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        List<User> users = userDao.searchUser("ma");
        assertEquals(1, users.size());
        assertEquals(userDao.getUserById(1), users.get(0));
    }
}
