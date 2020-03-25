package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.entity.User;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class JdbcUserDaoITest {

    private JdbcUserDao jdbcUserDao = ServiceLocator.getService(JdbcUserDao.class);

    @Ignore
    @Test
    public void testGetAll() {
        List<User> users = jdbcUserDao.getAll();
        assertFalse(users.isEmpty());
        for (User user : users) {
            assertNotEquals(user.getId(), 0);
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
            assertNotEquals(user.getSalary(), 0.0);
            assertNotNull(user.getSalary());
        }
    }
}
