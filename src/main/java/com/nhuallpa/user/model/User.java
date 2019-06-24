package com.nhuallpa.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Contain the detail of user")
public class User {

  private static final int ALLOWED_AGE = 18;

  @Id
  @GeneratedValue
  private Integer id;

  @Size(min=2, max = 100)
  @ApiModelProperty(notes = "The name should be between  2 y 100 caracters")
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

  @ApiModelProperty(notes = "The user should be gather than 18 year aold")
  @NotNull @Past
  private Date birth;


  public User(String name, DocumentType documentType, int documentNumber,
              Gender gender, Nationality nationality, String email, Date birth) {
    this.name = name;
    this.documentType = documentType;
    this.documentNumber = documentNumber;
    this.gender = gender;
    this.nationality = nationality;
    this.email = email;
    this.birth = birth;
  }

  public User copyValues(User user) {
    this.setName(user.getName());
    this.setNationality(user.getNationality());
    this.setGender(user.getGender());
    this.setEmail(user.getEmail());
    this.setDocumentType(user.getDocumentType());
    this.setDocumentNumber(user.getDocumentNumber());
    this.setBirth(user.getBirth());
    return this;
  }

  public boolean hasAllowedAge() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -ALLOWED_AGE);
    return (this.getBirth().compareTo(cal.getTime()) <= 0);
  }
}
