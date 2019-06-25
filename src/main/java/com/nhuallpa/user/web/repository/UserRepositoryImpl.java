package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

  @Override
  public User findByUser(User user) {
    Query query = em.createNativeQuery("SELECT * FROM user WHERE document_type = :document_type " +
                                                "and document_number = :document_number " +
                                                "and nationality = :nationality " +
                                                "and gender = :gender", User.class);
    query.setParameter("document_type", user.getDocumentType().ordinal());
    query.setParameter("document_number", user.getDocumentNumber());
    query.setParameter("nationality", user.getNationality().ordinal());
    query.setParameter("gender", user.getGender().name());

    try {
      return (User) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Long countByGender(Gender gender) {
    Query query = em.createNativeQuery("SELECT COUNT(*) FROM user WHERE gender = :gender");
    query.setParameter("gender", gender.name());
    return ((Number)query.getSingleResult()).longValue();
  }

  @Override
  public Long countByNationality(Nationality nationality) {
    Query query = em.createNativeQuery("SELECT COUNT(*) FROM user WHERE nationality = :nationality");
    query.setParameter("nationality", nationality.ordinal());
    return ((Number)query.getSingleResult()).longValue();
  }
}
