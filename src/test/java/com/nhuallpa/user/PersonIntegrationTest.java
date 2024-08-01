package com.nhuallpa.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuallpa.user.model.*;
import com.nhuallpa.user.web.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PersonRepository personRepository;

  private Person ernesto;

  private Person nestor;
  private Person milena;
  private Person candela;

  private Person gustavo;
  private Person lautaro;



  @Before
  public void setUp() {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);


    ernesto = new Person();
    ernesto.setName("Ernesto");
    ernesto.setDocumentType(DocumentType.DNI);
    ernesto.setDocumentNumber(99883231);
    ernesto.setGender(Gender.M);
    ernesto.setNationality(Nationality.ARGENTINA);
    ernesto.setBirth(cal.getTime());
    ernesto.setEmail("email@gmaila.com");


    nestor = new Person();
    nestor.setName("Nestor");
    nestor.setDocumentType(DocumentType.DNI);
    nestor.setDocumentNumber(1212222);
    nestor.setGender(Gender.M);
    nestor.setNationality(Nationality.ARGENTINA);
    nestor.setBirth(cal.getTime());
    nestor.setEmail("mi@gmail.com");

    milena = new Person();
    milena.setName("Milena");
    milena.setDocumentType(DocumentType.DNI);
    milena.setDocumentNumber(32998844);
    milena.setGender(Gender.F);
    milena.setNationality(Nationality.ARGENTINA);
    milena.setBirth(cal.getTime());
    milena.setEmail("email@gmaila.com");

    candela = new Person();
    candela.setName("Candela");
    candela.setDocumentType(DocumentType.DNI);
    candela.setDocumentNumber(99998844);
    candela.setGender(Gender.F);
    candela.setNationality(Nationality.ARGENTINA);
    candela.setBirth(cal.getTime());
    candela.setEmail("email@gmaila.com");

    gustavo = new Person();
    gustavo.setName("Gustavo");
    gustavo.setDocumentType(DocumentType.DNI);
    gustavo.setDocumentNumber(32244553);
    gustavo.setGender(Gender.M);
    gustavo.setNationality(Nationality.ARGENTINA);
    gustavo.setBirth(cal.getTime());
    gustavo.setEmail("email@gmaila.com");

    lautaro = new Person();
    lautaro.setName("Lautaro");
    lautaro.setDocumentType(DocumentType.DNI);
    lautaro.setDocumentNumber(64323344);
    lautaro.setGender(Gender.M);
    lautaro.setNationality(Nationality.ARGENTINA);
    lautaro.setBirth(cal.getTime());
    lautaro.setEmail("email@gmail.com");

  }

  @Test
  public void createUserAllowed() throws Exception {
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(nestor)))
            .andExpect(status().isCreated());

    List<Person> personFound = personRepository.findUserByName("Nestor");
    assertFalse(personFound.isEmpty());
    assertEquals("mi@gmail.com", personFound.get(0).getEmail());
  }

  @Test
  public void createAUserNotAllowedByAge() throws Exception {
    Person person = new Person("Nestor", DocumentType.DNI,34556777, Gender.M, Nationality.ARGENTINA, "mi@gmail.com", Calendar.getInstance().getTime());

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(person)))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void createDuplicateUserDoNotAllowed() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    Person person = new Person("Nestor", DocumentType.DNI,34556777, Gender.M, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(person)))
            .andExpect(status().isCreated());

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(person)))
            .andExpect(status().isConflict());
  }

  @Test
  public void createRelationshipParentChildSibling() throws Exception {

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(nestor)))
            .andExpect(status().isCreated());

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(milena)))
            .andExpect(status().isCreated());

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(candela)))
            .andExpect(status().isCreated());

    List<Person> personFound = personRepository.findUserByName("Nestor");
    Person parent = personFound.get(0);

    personFound = personRepository.findUserByName("Milena");
    Person child = personFound.get(0);

    personFound = personRepository.findUserByName("Candela");
    Person secondChild = personFound.get(0);


    mockMvc.perform(post("/person/" + parent.getId() + "/parent/" + child.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    mockMvc.perform(post("/person/" + parent.getId() + "/parent/" + secondChild.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    mockMvc.perform(get("/relationship/" + child.getId() + "/" + secondChild.getId())
            .contentType("application/json"))
            .andExpect(jsonPath("$.relationship", is(Relationship.HERMANO.name())))
            .andExpect(status().isOk());
  }


  @Test
  public void createRelationshipParentChildCousin() throws Exception {

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(ernesto)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(nestor)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(milena)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(gustavo)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(lautaro)))
            .andExpect(status().isCreated());

    // Armamos relaciones
    List<Person> personFound = personRepository.findUserByName("Nestor");
    Person nestorFound = personFound.get(0);
    personFound = personRepository.findUserByName("Milena");
    Person milenaFound = personFound.get(0);
    mockMvc.perform(post("/person/" + nestorFound.getId() + "/parent/" + milenaFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    personFound = personRepository.findUserByName("Gustavo");
    Person gustavoFound = personFound.get(0);
    personFound = personRepository.findUserByName("Lautaro");
    Person lautaroFound = personFound.get(0);
    mockMvc.perform(post("/person/" + gustavoFound.getId() + "/parent/" + lautaroFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    personFound = personRepository.findUserByName("Ernesto");
    Person ernestoFound = personFound.get(0);
    mockMvc.perform(post("/person/" + ernestoFound.getId() + "/parent/" + gustavoFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());
    mockMvc.perform(post("/person/" + ernestoFound.getId() + "/parent/" + nestorFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    // Verificar relacion PRIMO
    mockMvc.perform(get("/relationship/" + lautaroFound.getId() + "/" + milenaFound.getId())
            .contentType("application/json"))
            .andExpect(jsonPath("$.relationship", is(Relationship.PRIMO.name())))
            .andExpect(status().isOk());
  }


  @Test
  public void createRelationshipParentChildUncle() throws Exception {

    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(ernesto)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(nestor)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(milena)))
            .andExpect(status().isCreated());
    mockMvc.perform(post("/person")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(gustavo)))
            .andExpect(status().isCreated());

    // Armamos relaciones
    List<Person> personFound = personRepository.findUserByName("Nestor");
    Person nestorFound = personFound.get(0);
    personFound = personRepository.findUserByName("Milena");
    Person milenaFound = personFound.get(0);
    mockMvc.perform(post("/person/" + nestorFound.getId() + "/parent/" + milenaFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    personFound = personRepository.findUserByName("Ernesto");
    Person ernestoFound = personFound.get(0);
    personFound = personRepository.findUserByName("Gustavo");
    Person gustavoFound = personFound.get(0);
    mockMvc.perform(post("/person/" + ernestoFound.getId() + "/parent/" + gustavoFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());
    mockMvc.perform(post("/person/" + ernestoFound.getId() + "/parent/" + nestorFound.getId())
            .contentType("application/json"))
            .andExpect(status().isOk());

    // Verificar relacion PRIMO
    mockMvc.perform(get("/relationship/" + gustavoFound.getId() + "/" + milenaFound.getId())
            .contentType("application/json"))
            .andExpect(jsonPath("$.relationship", is(Relationship.TIO.name())))
            .andExpect(status().isOk());
  }
}
