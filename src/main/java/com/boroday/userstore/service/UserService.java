package com.boroday.userstore.service;

import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;

import java.util.List;

public class UserService {

    private JdbcUserDao jdbcUserDao = new JdbcUserDao();

    public List<User> getAll() {
        return jdbcUserDao.getAll();
    }

    public int add(User user) {
        return jdbcUserDao.addNewUser(user);
    }

    public int remove(String userId) {
        return jdbcUserDao.removeUser(Integer.parseInt(userId));
    }

    public User getById(String userId) {
        return jdbcUserDao.getUserById(Integer.parseInt(userId));
    }

    public int update(User user) {
        return jdbcUserDao.updateUser(user);
    }

    public List<User> search(String text) {
        return jdbcUserDao.searchUser(text);
    }
}
