package com.nhuallpa.person.infrastructure.controller;

import com.nhuallpa.person.domain.model.Person;
import com.nhuallpa.person.domain.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class PersonController {

  public static final String PERSONS = "/persons";
  @Autowired
  private PersonService personService;

  @PostMapping(PERSONS)
  @ResponseBody public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
    Person personCreado = personService.create(person);
    if (personCreado == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(personCreado, HttpStatus.CREATED);
  }

  @GetMapping(PERSONS)
  @ResponseBody public ResponseEntity<List<Person>> getAll() {

    List<Person> personas = this.personService.findAll();

    if (personas.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(personas, HttpStatus.OK);
  }

  @GetMapping(value = PERSONS+"/{id}")
  @ResponseBody public ResponseEntity<Person> getById(@PathVariable("id") UUID id) {

    Person person = personService.findById(id);
    if (person == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(person, HttpStatus.OK);
  }

  @DeleteMapping(value = PERSONS+"/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
    Person person = personService.findById(id);
    if (person == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    personService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping(PERSONS)
  public ResponseEntity<Person> update(@RequestBody Person person) {

    Person currentPerson = personService.update(person);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(currentPerson, HttpStatus.OK);
  }

  @PostMapping(value = PERSONS+"/{id}/parent/{idChild}")
  public ResponseEntity<Person> setParent(@PathVariable("id") UUID id,
                                          @PathVariable("idChild") UUID idChild) {

    Person person = personService.findById(id);
    if (person == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Person personChild = personService.findById(idChild);
    if (personChild == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    personChild.setParent(person);
    Person currentPerson = personService.update(personChild);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(currentPerson, HttpStatus.OK);
  }
}
