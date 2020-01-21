package com.boroday.userstore.service;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;

import java.util.List;

public class UserService {

    private JdbcUserDao jdbcUserDao = new JdbcUserDao();

    public List<User> getAll() {
        return jdbcUserDao.getAll();
    }

    public void add(User user) {
        jdbcUserDao.addNewUser(user);
    }

    public void remove(String userId) {
        jdbcUserDao.removeUser(Integer.parseInt(userId));
    }

    public User getById(String userId) {
        return jdbcUserDao.getUserById(Integer.parseInt(userId));
    }

    public void update(User user) {
        jdbcUserDao.updateUser(user);
    }

    public List<User> search(String text) {
        return jdbcUserDao.searchUser(text);
    }
}
