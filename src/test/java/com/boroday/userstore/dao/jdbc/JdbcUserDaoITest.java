package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;

public class JdbcUserDaoITest {

    @Test
    public void testGetAll(){
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        List<User> users = jdbcUserDao.getAll();
        assertFalse(users.isEmpty());
        for (User user: users){
            assertNotNull(user.getId());
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
        }
    }
}
