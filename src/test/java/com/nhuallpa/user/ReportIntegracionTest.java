package com.nhuallpa.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuallpa.person.domain.model.DocumentType;
import com.nhuallpa.person.domain.model.Gender;
import com.nhuallpa.person.domain.model.Nationality;
import com.nhuallpa.person.domain.model.Person;
import com.nhuallpa.person.infrastructure.controller.ReportController;
import com.nhuallpa.person.domain.repository.PersonRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportIntegracionTest {

  public static final String PERSON_URL = "/persons";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PersonRepository personRepository;

  @After
  public void teardown() {
    personRepository.deleteAll();
  }

  @Test
  public void reportEmpty() throws Exception {
    mockMvc.perform(get(ReportController.STATS_URL)
                    .param("format", "web")
            .contentType("application/json"))
            .andExpect(jsonPath("$.totalMale", is(0)))
            .andExpect(jsonPath("$.totalFemale", is(0)))
            .andExpect(jsonPath("$.argentinePercentage", is(0)))
            .andExpect(status().isOk());
  }
  @Test
  public void reportOneMakeOneFemaleArgentine() throws Exception {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);

    Person personMale = new Person("Nestor", DocumentType.DNI,34556777, Gender.M, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());
    mockMvc.perform(post(PERSON_URL)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(personMale)))
            .andExpect(status().isCreated());

    Person personFemale = new Person("Carla", DocumentType.DNI,93222121, Gender.F, Nationality.EXTRANGERO, "ll@gmail.com", cal.getTime());
    mockMvc.perform(post(PERSON_URL)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(personFemale)))
            .andExpect(status().isCreated());

    mockMvc.perform(get(ReportController.STATS_URL)
                    .param("format", "web")
            .contentType("application/json"))
            .andExpect(jsonPath("$.totalMale", is(1)))
            .andExpect(jsonPath("$.totalFemale", is(1)))
            .andExpect(jsonPath("$.argentinePercentage", is(50)))
            .andExpect(status().isOk());
  }

}
