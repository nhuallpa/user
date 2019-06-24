package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

  @PersistenceContext
  EntityManager em;
  
  @Override
  public List<User> findUserByName(String name) {
    Query query = em.createNativeQuery("SELECT * FROM user WHERE name = :name", User.class);
    query.setParameter("name", name);
    return query.getResultList();
  }
}
