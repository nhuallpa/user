package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.User;

import java.util.List;

public interface UserRepositoryCustom {

  List<User> findUserByName(String name);

  User findByUser(User user);

  Long countByGender(Gender gender);

  Long countByNationality(Nationality nationality);
}
