package com.boroday.userstore.dao.jdbc;
import com.boroday.userstore.entity.User;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

public class JdbcUserDaoITest {

    JdbcUserDao jdbcUserDao = new JdbcUserDao();

    @Test
    public void testGetAll() {
        List<User> users = jdbcUserDao.getAll();
        assertFalse(users.isEmpty());
        for (User user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
        }
    }

    @Test
    public void testAddNewUser() {
        User user = new User();
        user.setFirstName("Michael");
        user.setLastName("Kanda");
        int countOfUsers = jdbcUserDao.addNewUser(user);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testRemoveUser() {
        int countOfUsers = jdbcUserDao.removeUser(22);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testGetUserById() {
        User user = jdbcUserDao.getUserById(2);
        assertEquals("Michael1", user.getFirstName());
        assertEquals("Hay1", user.getLastName());
        assertEquals(15, user.getId());
        assertEquals(null, user.getSalary());
        assertEquals(LocalDate.of(1992, Month.APRIL, 15), user.getDateOfBirth());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(15);
        user.setFirstName("Sonja");
        user.setLastName("Morr");
        user.setSalary(null);
        user.setDateOfBirth(null);
        int countOfUsers = jdbcUserDao.updateUser(user);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testSearchUser() {
        List<User> list = jdbcUserDao.searchUser("om");
        assertEquals(1, list.size());
    }
}
