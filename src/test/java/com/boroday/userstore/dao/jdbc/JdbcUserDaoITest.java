package com.boroday.userstore.dao.jdbc;
import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.util.TestDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserDaoITest {

    private TestDataSource testDataSource = new TestDataSource();
    private JdbcDataSource jdbcDataSource;
    private List<User> expectedUsers;
    private User secondUser;

    @BeforeEach
    public void createUsersList() {
        jdbcDataSource = testDataSource.init();
        User firstUser = new User();
        firstUser.setId(1);
        firstUser.setLogin("ama");
        firstUser.setPassword("123");
        firstUser.setFirstName("Amadina");
        firstUser.setLastName("Troll");
        firstUser.setSalary(1500.00);
        LocalDate localDateFirst = LocalDate.of(1993, 05, 13);
        firstUser.setDateOfBirth(localDateFirst);

        secondUser = new User();
        secondUser.setId(2);
        secondUser.setLogin("pav");
        secondUser.setPassword("456");
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
    public void removeUsers() {
        testDataSource.cleanup();
    }

    @Test
    public void testGetAll() {
        //prepare
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //when
        List<User> users = userDao.getAll();

        //then
        assertEquals(users.size(), 2);
        for (User expectedUser : expectedUsers) {
            assertTrue(users.contains(expectedUser));
        }
    }

    @Test
    public void testAddNewUser() {
        //prepare
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        User newUser = new User();
        newUser.setId(3);
        newUser.setLogin("eve");
        newUser.setPassword("eve123");
        newUser.setFirstName("Evelina");
        newUser.setLastName("Rosdorf");
        newUser.setSalary(1300.00);
        LocalDate localDateSecond = LocalDate.of(1980, 7, 24);
        newUser.setDateOfBirth(localDateSecond);

        //when
        userDao.addNewUser(newUser);

        //then
        assertEquals(newUser, userDao.getUserById(3));
    }

    @Test
    public void testRemoveUser() {
        //prepare
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        User savedUser = userDao.getUserById(2);

        //when
        userDao.removeUser(2);

        //then
        List<User> users = userDao.getAll();
        assertEquals(1, users.size());
        assertNotEquals(users.get(0), savedUser);
    }

    @Test
    public void testGetUserById() {
        //when
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //then
        assertEquals(secondUser, userDao.getUserById(2));
    }

    @Test
    public void testGetUserByLogin() {
        //when
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //then
        assertEquals(secondUser, userDao.getUserByLogin("pav", "456"));
    }

    @Test
    public void testGetUserByLoginIncorrectLogin() {
        //when
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //then
        assertNull(userDao.getUserByLogin("pav1", "456"));
    }

    @Test
    public void testGetUserByLoginIncorrectPassword() {
        //when
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //then
        assertNull(userDao.getUserByLogin("pav", "457"));
    }

    @Test
    public void testUpdateUser() {
        //prepare
        UserDao userDao = new JdbcUserDao(jdbcDataSource);
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setLogin("mar");
        updatedUser.setPassword("mar123");
        updatedUser.setFirstName("Mary");
        updatedUser.setLastName("Hops");
        updatedUser.setSalary(1600.00);
        LocalDate localDateSecond = LocalDate.of(1990, 2, 12);
        updatedUser.setDateOfBirth(localDateSecond);

        //when
        userDao.updateUser(updatedUser);

        //then
        assertEquals(updatedUser, userDao.getUserById(1));
    }

    @Test
    public void testSearchUser() {
        //prepare
        UserDao userDao = new JdbcUserDao(jdbcDataSource);

        //when
        List<User> users = userDao.searchUser("ma");

        //then
        assertEquals(1, users.size());
        assertEquals(userDao.getUserById(1), users.get(0));
    }
}