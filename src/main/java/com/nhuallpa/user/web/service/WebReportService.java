package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Report;
import com.nhuallpa.user.web.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@CacheConfig(cacheNames = {"report"})
public class WebReportService implements ReportService {

  private static final Logger logger = LoggerFactory.getLogger(WebReportService.class);

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
    return new Report(countUserMale, countUserFemale, percentage);
  }
}
