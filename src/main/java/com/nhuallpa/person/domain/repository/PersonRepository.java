package com.nhuallpa.person.domain.repository;

import com.nhuallpa.person.domain.model.Gender;
import com.nhuallpa.person.domain.model.Nationality;
import com.nhuallpa.person.domain.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public interface PersonRepository extends CrudRepository<Person, UUID> {

  Long countByGender(Gender gender);

  Long countByNationality(Nationality nationality);

  Optional<Person> findByDocumentNumber(int documentNumber);

  List<Person> findUserByName(String name);

}
