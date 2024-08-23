package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID>, PersonRepositoryCustom {

  Long countByGender(Gender gender);

  Long countByNationality(Nationality nationality);

}
