package com.nhuallpa.person.domain.service;

import com.nhuallpa.person.domain.model.Gender;
import com.nhuallpa.person.domain.model.Nationality;
import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("WebReportService")
@CacheConfig(cacheNames = {"report"})
public class PersonReportService implements ReportAnalyzerService {

  private static final Logger logger = LoggerFactory.getLogger(PersonReportService.class);
  public static final String target = "web";

  @Autowired
  private PersonRepository userRepository;

  @Override
  @Cacheable
  public Report generateReport() {
    logger.info("Consulta de reporte");
    Long countUserFemale = userRepository.countByGender(Gender.F);
    Long countUserMale = userRepository.countByGender(Gender.M);
    Long countArgentine = userRepository.countByNationality(Nationality.ARGENTINA);
    Long countAllUser = userRepository.count();

    Long percentage = (countAllUser != 0) ? Math.abs(countArgentine * 100 / countAllUser) : 0;
    return new Report(target, countUserMale, countUserFemale, percentage);
  }
}
