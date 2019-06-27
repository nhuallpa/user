package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepositoryCustom {

  @PersistenceContext
  EntityManager em;
  
  @Override
  public List<Person> findUserByName(String name) {
    Query query = em.createNativeQuery("SELECT * FROM person WHERE name = :name", Person.class);
    query.setParameter("name", name);
    return query.getResultList();
  }

  @Override
  public Person findByPerson(Person person) {
    Query query = em.createNativeQuery("SELECT * FROM person WHERE document_type = :document_type " +
                                                "and document_number = :document_number " +
                                                "and nationality = :nationality " +
                                                "and gender = :gender", Person.class);
    query.setParameter("document_type", person.getDocumentType().ordinal());
    query.setParameter("document_number", person.getDocumentNumber());
    query.setParameter("nationality", person.getNationality().ordinal());
    query.setParameter("gender", person.getGender().name());

    try {
      return (Person) query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Long countByGender(Gender gender) {
    Query query = em.createNativeQuery("SELECT COUNT(*) FROM person WHERE gender = :gender");
    query.setParameter("gender", gender.name());
    return ((Number)query.getSingleResult()).longValue();
  }

  @Override
  public Long countByNationality(Nationality nationality) {
    Query query = em.createNativeQuery("SELECT COUNT(*) FROM person WHERE nationality = :nationality");
    query.setParameter("nationality", nationality.ordinal());
    return ((Number)query.getSingleResult()).longValue();
  }
}
