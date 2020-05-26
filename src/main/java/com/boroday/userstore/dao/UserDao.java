package com.boroday.userstore.dao;

import com.boroday.userstore.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    void addNewUser(User user);
    void removeUser(long userId);
    User getUserById(long userId);
    User getUserByLogin(String userLogin, String userPassword);
    void updateUser(User user);
    List<User> searchUser(String text);
}
