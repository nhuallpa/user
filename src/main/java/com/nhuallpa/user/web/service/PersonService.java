package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.Person;
import com.nhuallpa.user.model.Relationship;

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
