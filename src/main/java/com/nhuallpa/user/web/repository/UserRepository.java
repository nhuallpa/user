package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {


}
