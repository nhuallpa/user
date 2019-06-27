package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Person;

import java.util.List;

public interface PersonRepositoryCustom {

  List<Person> findUserByName(String name);

  Person findByPerson(Person person);

  Long countByGender(Gender gender);

  Long countByNationality(Nationality nationality);
}
