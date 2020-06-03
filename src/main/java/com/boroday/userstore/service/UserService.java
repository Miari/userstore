package com.boroday.userstore.service;

import com.boroday.userstore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    void add(User user);

    void remove(long userId); //todo change to long

    Optional<User> getById(long userId);

    Optional<User> getByLogin(String userLogin, String userPassword);

    void update(User user);

    List<User> search(String text);
}
