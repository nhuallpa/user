package com.nhuallpa.person.domain.service;

import com.nhuallpa.person.domain.model.Person;
import com.nhuallpa.person.domain.model.Relationship;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    Person create(Person person);

    List<Person> findAll();

    Person findById(UUID id);

    Person update(Person person);

    void deleteById(UUID id);

    Relationship getRelationship(UUID id, UUID idOther);
}
