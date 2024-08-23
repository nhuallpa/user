package com.nhuallpa.user.web.service;

import com.nhuallpa.user.exception.ConflictException;
import com.nhuallpa.user.exception.GeneralException;
import com.nhuallpa.user.model.Person;
import com.nhuallpa.user.model.Relationship;
import com.nhuallpa.user.web.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public Person create(Person person) {
    if (!person.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }

    Person personExist = this.personRepository.findByPerson(person);
    if (personExist != null) {
      throw new ConflictException(String.format("Person %s already exist", person.getName()));
    }
    person.setId(UUID.randomUUID());
    return this.personRepository.save(person);
  }

  public List<Person> findAll() {
    return StreamSupport.stream(this.personRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  @Override
  public Person findById(UUID id) {
    return this.personRepository.findById(id).orElse(null);
  }


  @Override
  public Person update(Person person) {
    if (!person.hasAllowedAge()) {
      throw new GeneralException("Age do not allowed");
    }
    Optional<Person> currentPerson = personRepository.findById(person.getId());
    if (currentPerson.isPresent()) {
      return this.personRepository.save(currentPerson.get().copyValues(person));
    } else {
      return null;
    }
  }

  @Override
  public void deleteById(UUID id) {
    personRepository.deleteById(id);
  }


  @Override
  public Relationship getRelationship(UUID id, UUID idOther) {

    Person aPerson = this.findById(id);
    Person otherPerson = this.findById(idOther);
    if (aPerson == null || otherPerson == null) {
      throw new GeneralException("Person not found");
    }

    return aPerson.getRelationshipOf(otherPerson);
  }

}
