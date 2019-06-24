package com.nhuallpa.user;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.DocumentType;
import com.nhuallpa.user.model.User;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.Calendar;

public class UserTest {


  @Test
  public void validacionUsuarioEsMenorDeEdad() {
    User user = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", Calendar.getInstance().getTime());
    assertFalse(user.hasAllowedAge());
  }

  @Test
  public void validacionUsuarioEsMayorDeEdad() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -28);
    User user = new User("Nestor", DocumentType.DNI,34556777, Gender.MALE, Nationality.ARGENTINA, "mi@gmail.com", cal.getTime());
    assertTrue(user.hasAllowedAge());
  }
}
