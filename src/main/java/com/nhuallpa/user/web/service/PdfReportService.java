package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Report;
import com.nhuallpa.user.web.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PdfReportService {

  private static final Logger logger = LoggerFactory.getLogger(PdfReportService.class);

  private PersonRepository userRepository;

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
