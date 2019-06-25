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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportIntegracionTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void reportEmpty() throws Exception {
    mockMvc.perform(get("/estadisticas")
            .contentType("application/json"))
            .andExpect(jsonPath("$.cantidad_hombres", is(0)))
            .andExpect(jsonPath("$.cantidad_mujeres", is(0)))
            .andExpect(jsonPath("$.porcentaje_argentinos", is(0)))
            .andExpect(status().isOk());
  }
  @Test
  public void reportOneMakeOneFemaleArgentine() throws Exception {

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);

    User userMale = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());
    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userMale)))
            .andExpect(status().isCreated());

    User userFemale = new User("Carla", DocumentType.DNI,93222121, Gender.FEMALE, Nationality.EXTRANGERO, "ll@gmail.com", cal.getTime());
    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userFemale)))
            .andExpect(status().isCreated());

    mockMvc.perform(get("/estadisticas")
            .contentType("application/json"))
            .andExpect(jsonPath("$.cantidad_hombres", is(1)))
            .andExpect(jsonPath("$.cantidad_mujeres", is(1)))
            .andExpect(jsonPath("$.porcentaje_argentinos", is(50)))
            .andExpect(status().isOk());
  }

}
