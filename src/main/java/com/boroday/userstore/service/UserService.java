package com.boroday.userstore.service;

import com.boroday.userstore.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void add(User user);

    void remove(String userId);

    User getById(String userId);

    void update(User user);

    List<User> search(String text);
}
