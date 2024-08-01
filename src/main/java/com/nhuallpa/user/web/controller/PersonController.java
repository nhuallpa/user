package com.nhuallpa.user.web.controller;

import com.nhuallpa.user.model.Person;
import com.nhuallpa.user.web.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService personService;

  @PostMapping()
  @ResponseBody public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
    Person personCreado = personService.create(person);
    if (personCreado == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(personCreado, HttpStatus.CREATED);
  }

  @GetMapping()
  @ResponseBody public ResponseEntity<List<Person>> getAll() {

    List<Person> personas = this.personService.findAll();

    if (personas.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(personas, HttpStatus.OK);
  }

  @GetMapping(value = "{id}")
  @ResponseBody public ResponseEntity<Person> getById(@PathVariable("id") Integer id) {

    Person person = personService.findById(id);
    if (person == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(person, HttpStatus.OK);
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
    Person person = personService.findById(id);
    if (person == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    personService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Person> update(@RequestBody Person person) {

    Person currentPerson = personService.update(person);
    if (currentPerson == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(currentPerson, HttpStatus.OK);
  }

  @PostMapping(value = "{id}/parent/{idChild}")
  public ResponseEntity<Person> setParent(@PathVariable("id") Integer id, @PathVariable("idChild") Integer idChild) {

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
