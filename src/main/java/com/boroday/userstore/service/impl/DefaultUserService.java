package com.boroday.userstore.service.impl;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {

    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void add(User user) {
        userDao.addNewUser(user);
    }

    public void remove(String userId) {
        userDao.removeUser(Integer.parseInt(userId));
    }

    public User getById(String userId) {
        return userDao.getUserById(Integer.parseInt(userId));
    }

    public void update(User user) {
        userDao.updateUser(user);
    }

    public List<User> search(String text) {
        return userDao.searchUser(text);
    }
}
