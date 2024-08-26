package com.nhuallpa.user;

import com.nhuallpa.person.domain.model.Gender;
import com.nhuallpa.person.domain.model.Nationality;
import com.nhuallpa.person.domain.model.DocumentType;
import com.nhuallpa.person.domain.model.Person;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.Calendar;

public class PersonTest {


  @Test
  public void validacionUsuarioEsMenorDeEdad() {
    Person person = new Person("Nestor", DocumentType.DNI,34556777, Gender.M, Nationality.ARGENTINA, "mi@gmail.com", Calendar.getInstance().getTime());
    assertFalse(person.hasAllowedAge());
  }

  @Test
  public void validacionUsuarioEsMayorDeEdad() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    Person person = new Person("Nestor", DocumentType.DNI,34556777, Gender.M, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());
    assertTrue(person.hasAllowedAge());
  }
}
