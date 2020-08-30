package com.boroday.userstore.service;

import com.boroday.userstore.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void add(User user);

    void remove(long userId); //todo change to long

    User getById(long userId);

    User getByLogin(String userLogin, String userPassword);

    void update(User user);

    List<User> search(String text);
}
