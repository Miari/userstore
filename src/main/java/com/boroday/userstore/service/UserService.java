package com.boroday.userstore.service;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserService {

    private JdbcUserDao jdbcUserDao;

    public void setJdbcUserDao(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public List<User> getAll() {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.getAll();
    }

    public int addNewUser(HttpServletRequest request) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.addNewUser(request);
    }

    public int removeUser(String userId) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.removeUser(Integer.parseInt(userId));
    }

    public User getUserById(String userId) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.getUserById(Integer.parseInt(userId));
    }

    public int updateUser(HttpServletRequest request) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.updateUser(request);
    }
}
