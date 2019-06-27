package com.nhuallpa.user.web.repository;

import com.nhuallpa.user.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer>, PersonRepositoryCustom {


}
