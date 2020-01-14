package com.boroday.userstore.service;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;

import java.util.List;

public class UserService {

    private JdbcUserDao jdbcUserDao;

    public List<User> getAll() {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.getAll();
    }

    public int addNewUser(User user) {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.addNewUser(user);
    }

    public int removeUser(String userId) {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.removeUser(Integer.parseInt(userId));
    }

    public User getUserById(String userId) {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.getUserById(Integer.parseInt(userId));
    }

    public int updateUser(User user) {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.updateUser(user);
    }

    public List<User> searchUser(String text) {
        jdbcUserDao = new JdbcUserDao();
        return jdbcUserDao.searchUser(text);
    }
}
