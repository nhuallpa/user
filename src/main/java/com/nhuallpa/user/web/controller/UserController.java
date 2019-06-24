package com.nhuallpa.user.web.controller;

import com.nhuallpa.user.model.User;
import com.nhuallpa.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping()
  @ResponseBody public ResponseEntity<User> create(@Valid @RequestBody User user) {
    User userCreado = userService.create(user);
    if (userCreado == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(userCreado, HttpStatus.CREATED);
  }

  @GetMapping()
  @ResponseBody public ResponseEntity<List<User>> getAll() {

    List<User> users = this.userService.findAll();

    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping(value = "{id}")
  @ResponseBody public ResponseEntity<User> getById(@PathVariable("id") Integer id) {

    User user = userService.findById(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
    User user = userService.findById(id);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    userService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<User> update(@RequestBody User user) {

    User currentUser = userService.update(user);
    if (currentUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(currentUser, HttpStatus.OK);
  }
}
