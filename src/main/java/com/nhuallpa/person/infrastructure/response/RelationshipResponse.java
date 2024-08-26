package com.nhuallpa.person.infrastructure.response;

import com.nhuallpa.person.domain.model.Relationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor
public class RelationshipResponse {

  private Relationship relationship;

}
