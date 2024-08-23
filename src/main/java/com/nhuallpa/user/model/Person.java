package com.nhuallpa.user.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "person", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Person {

  private static final int ALLOWED_AGE = 18;

  @Id
  @Type(type="pg-uuid")
  private UUID id;

  @Size(min=2, max = 100)
  private String name;

  @NotNull
  @Column(name = "document_type")
  @Enumerated(EnumType.ORDINAL)
  private DocumentType documentType;

  @NotNull
  @Column(name = "document_number")
  private int documentNumber;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.ORDINAL)
  private Nationality nationality;

  @NotEmpty @Email
  private String email;

  @NotNull @Past
  private Date birthdate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_id")
  private Person parent;

  public Person(String name, DocumentType documentType, int documentNumber,
                Gender gender, Nationality nationality, String email, Date birth) {
    this.name = name;
    this.documentType = documentType;
    this.documentNumber = documentNumber;
    this.gender = gender;
    this.nationality = nationality;
    this.email = email;
    this.birthdate = birth;
  }

  public Person copyValues(Person person) {
    this.setName(person.getName());
    this.setNationality(person.getNationality());
    this.setGender(person.getGender());
    this.setEmail(person.getEmail());
    this.setDocumentType(person.getDocumentType());
    this.setDocumentNumber(person.getDocumentNumber());
    this.setBirthdate(person.getBirthdate());
    this.setParent(person.getParent());
    return this;
  }

  public boolean hasAllowedAge() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -ALLOWED_AGE);
    return (this.getBirthdate().compareTo(cal.getTime()) <= 0);
  }

  public boolean isSiblingOf(Person otherPerson) {
    return (this.getParent() != null && otherPerson.getParent() != null &&
            (this.getParent().getId() == otherPerson.getParent().getId()));
  }

  public boolean isCousinOf(Person otherPerson) {
    return (this.getParent() != null && otherPerson.getParent() != null
          && this.getParent().isSiblingOf(otherPerson.getParent()));
  }

  public boolean isUncleOf(Person otherPerson) {
    return (otherPerson.getParent() != null && this.isSiblingOf(otherPerson.getParent()));
  }

  public Relationship getRelationshipOf(Person otherPerson) {
    Relationship relationship = Relationship.NINGUNO;

    if (this.isSiblingOf(otherPerson)) {
      relationship = Relationship.HERMANO;
    } else if (this.isCousinOf(otherPerson)) {
      relationship = Relationship.PRIMO;
    } else if (this.isUncleOf(otherPerson)) {
      relationship = Relationship.TIO;
    }
    return relationship;
  }
}
