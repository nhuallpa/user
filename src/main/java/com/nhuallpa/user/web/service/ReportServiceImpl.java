package com.nhuallpa.user.web.service;

import com.nhuallpa.user.model.Gender;
import com.nhuallpa.user.model.Nationality;
import com.nhuallpa.user.model.Report;
import com.nhuallpa.user.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Report generateReport() {
    /**Long countUserFemale = userRepository.countByGender(Gender.FEMALE);
    Long countUserMale = userRepository.countByGender(Gender.MALE);
    Long countArgentine = userRepository.countByNationality(Nationality.ARGENTINA);
    Long countAllUser = userRepository.count(); */
    return new Report();
  }
}
