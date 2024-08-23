package com.nhuallpa.user.web.controller;

import com.nhuallpa.user.model.Relationship;
import com.nhuallpa.user.web.response.RelationshipResponse;
import com.nhuallpa.user.web.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/relationship")
public class RelationshipController {

  @Autowired
  private PersonService personService;

  @GetMapping(value = "{id}/{idOther}")
  public ResponseEntity<RelationshipResponse> getRelationship(
          @PathVariable("id") UUID id, @PathVariable("idOther") UUID idOther) {
    Relationship relationship = personService.getRelationship(id, idOther);
    return new ResponseEntity<RelationshipResponse>(new RelationshipResponse(relationship), HttpStatus.OK);
  }
}
