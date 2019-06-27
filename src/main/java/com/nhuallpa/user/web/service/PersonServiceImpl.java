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
      throw new ConflictException("Person already exist");
    }
    return this.personRepository.save(person);
  }

  public List<Person> findAll() {
    return this.personRepository.findAll();
  }

  @Override
  public Person findById(Integer id) {
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
  public void deleteById(Integer id) {
    personRepository.deleteById(id);
  }


  @Override
  public Relationship getRelationship(Integer id, Integer idOther) {

    Person aPerson = this.findById(id);
    Person otherPerson = this.findById(idOther);
    if (aPerson == null || otherPerson == null) {
      throw new GeneralException("Person not found");
    }

    return aPerson.getRelationshipOf(otherPerson);
  }

}
