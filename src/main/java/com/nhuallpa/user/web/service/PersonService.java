package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.Person;
import com.nhuallpa.user.model.Relationship;

import java.util.List;

public interface PersonService {

    Person create(Person person);

    List<Person> findAll();

    Person findById(Integer id);

    Person update(Person person);

    void deleteById(Integer id);

    Relationship getRelationship(Integer id, Integer idOther);
}
