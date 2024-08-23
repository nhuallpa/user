package com.nhuallpa.user;

import com.nhuallpa.user.model.DocumentType;
import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Person;
import com.nhuallpa.user.web.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonRepositoryTest {

  @Autowired
  private PersonRepository userRepository;

  @Test
  public void createRelationshipTest() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);

    Person person1 = new Person();
    person1.setName("Carlos");
    person1.setDocumentType(DocumentType.DNI);
    person1.setDocumentNumber(1212222);
    person1.setGender(Gender.M);
    person1.setNationality(Nationality.ARGENTINA);
    person1.setBirthdate(cal.getTime());
    person1.setEmail("email@gmaila.com");

    Person person2 = new Person();
    person2.setName("Milena");
    person2.setDocumentType(DocumentType.DNI);
    person2.setDocumentNumber(32998844);
    person2.setGender(Gender.F);
    person2.setNationality(Nationality.ARGENTINA);
    person2.setBirthdate(cal.getTime());
    person2.setEmail("email@gmaila.com");

    person2.setParent(person1);

    userRepository.save(person2);

    Optional<Person> personChild = userRepository.findById(person2.getId());

    assertTrue(personChild.isPresent());
    assertNotNull(personChild.get());
    assertNotNull(personChild.get().getParent());


    Optional<Person> personParent = userRepository.findById(personChild.get().getParent().getId());

    assertTrue(personParent.isPresent());
    assertNotNull(personParent.get());
    assertNull(personParent.get().getParent());

  }

}
