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

    private JdbcUserDao jdbcUserDao = new JdbcUserDao();

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
    public void testGetUserById() {
        User user = jdbcUserDao.getUserById(15);
        assertEquals("Sonja", user.getFirstName());
        assertEquals("Morr", user.getLastName());
        assertEquals(15, user.getId());
        assertEquals(2000.0, user.getSalary());
        assertEquals(LocalDate.of(2000, Month.JANUARY, 1), user.getDateOfBirth());
    }

    @Test
    public void testSearchUser() {
        List<User> list = jdbcUserDao.searchUser("li");
        assertEquals(5, list.size());
    }
}
