package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> findAll();

    User findById(Integer id);

    User update(User user);

    void deleteById(Integer id);
}
