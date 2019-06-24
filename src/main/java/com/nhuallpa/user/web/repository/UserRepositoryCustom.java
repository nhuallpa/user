package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.User;

import java.util.List;

public interface UserRepositoryCustom {

  List<User> findUserByName(String name);

}
