package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

public class JdbcUserDaoITest {

    JdbcUserDao jdbcUserDao;

    @Test
    public void testGetAll(){
        jdbcUserDao = new JdbcUserDao();
        List<User> users = jdbcUserDao.getAll();
        assertFalse(users.isEmpty());
        for (User user: users){
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
        }
    }

    @Test
    public void testAddNewUser(){
        jdbcUserDao = new JdbcUserDao();
        User user = new User();
        user.setFirstName("Jannet");
        user.setLastName("Yappel");
        int countOfUsers = jdbcUserDao.addNewUser(user);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testRemoveUser(){
        jdbcUserDao = new JdbcUserDao();
        int countOfUsers = jdbcUserDao.removeUser(22);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testGetUserById(){
        jdbcUserDao = new JdbcUserDao();
        User user = jdbcUserDao.getUserById(12);
        assertEquals("Michael1", user.getFirstName());
        assertEquals("Hay1", user.getLastName());
        assertEquals(12, user.getId());
        assertEquals(null, user.getSalary());
        assertEquals(LocalDate.of(1992, Month.APRIL, 15), user.getDateOfBirth());
    }

    @Test
    public void testUpdateUser(){
        jdbcUserDao = new JdbcUserDao();
        User user = new User();
        user.setId(12);
        user.setFirstName("Michael1");
        user.setLastName("Hay1");
        user.setSalary(null);
        user.setDateOfBirth(LocalDate.of(1992, Month.APRIL, 15));
        int countOfUsers = jdbcUserDao.updateUser(user);
        assertEquals(1, countOfUsers);
    }

    @Test
    public void testSearchUser() {
        jdbcUserDao = new JdbcUserDao();
        List<User> list = jdbcUserDao.searchUser("om");
        assertEquals(1, list.size());
    }
}
