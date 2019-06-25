package com.nhuallpa.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.DocumentType;
import com.nhuallpa.user.model.User;
import com.nhuallpa.user.web.repository.UserRepository;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository usuarioRepository;

  @Test
  public void createUserAllowed() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    User user = new User("Nestor", DocumentType.DNI,32009987, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated());

    List<User> userFound = usuarioRepository.findUserByName("Nestor");
    assertFalse(userFound.isEmpty());
    assertEquals("mi@gmail.com", userFound.get(0).getEmail());
  }

  @Test
  public void createAUserNotAllowedByAge() throws Exception {
    User user = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", Calendar.getInstance().getTime());

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void createDuplicateUserDoNotAllowed() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    User user = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated());

    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isConflict());
  }
}
