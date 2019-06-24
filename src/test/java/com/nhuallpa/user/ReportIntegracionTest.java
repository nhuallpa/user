package com.nhuallpa.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuallpa.user.model.DocumentType;
import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.User;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ReportIntegracionTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void resportEmpty() throws Exception {
    mockMvc.perform(get("/report")
            .contentType("application/json"))
            .andExpect(jsonPath("$.totalMale", is(0)))
            .andExpect(jsonPath("$.totalFemale", is(0)))
            .andExpect(jsonPath("$.argentineAverage", is(0)))
            .andExpect(status().isOk());
  }
  @Test
  public void resportOneMakeOneFemaleArgentine() throws Exception {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    User userMale = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userMale)))
            .andExpect(status().isCreated());

    User userFemale = new User("Carla", DocumentType.DNI,33222121, Gender.FEMALE, Nationality.ARGENTINA, "ll@gmail.com", cal.getTime());


    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userFemale)))
            .andExpect(status().isCreated());

    mockMvc.perform(get("/report")
            .contentType("application/json"))
            .andExpect(jsonPath("$.totalMale", is(1)))
            .andExpect(jsonPath("$.totalFemale", is(1)))
            .andExpect(jsonPath("$.argentineAverage", is(1)))
            .andExpect(status().isOk());
  }

}
