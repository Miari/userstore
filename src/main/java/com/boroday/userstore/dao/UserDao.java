package com.boroday.userstore.dao;

import com.boroday.userstore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAll();
    void addNewUser(User user);
    void removeUser(long userId);
    Optional<User> getUserById(long userId);
    Optional<User> getUserByLogin(String userLogin, String userPassword);
    void updateUser(User user);
    List<User> searchUser(String text);
}
