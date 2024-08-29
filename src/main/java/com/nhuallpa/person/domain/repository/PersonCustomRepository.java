package com.nhuallpa.person.domain.repository;

import com.nhuallpa.person.domain.model.Gender;
import com.nhuallpa.person.domain.model.Nationality;
import com.nhuallpa.person.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonCustomRepository {

  List<Person> findUserByName(String name);

  Long countByGender(Gender gender);

  Long countByNationality(Nationality nationality);

  Optional<Person> findByDocumentNumber(int documentNumber);

}
