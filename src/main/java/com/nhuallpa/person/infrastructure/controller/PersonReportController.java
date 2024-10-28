package com.nhuallpa.person.infrastructure.controller;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.domain.service.ReportAnalyzerService;
import com.nhuallpa.person.infrastructure.factory.ReportPresenterFactory;
import com.nhuallpa.person.infrastructure.presenter.WebReportPresenter;
import com.nhuallpa.person.infrastructure.response.ReportReponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class PersonReportController {

  public static final String STATS_URL = "/reports";

  private final ReportAnalyzerService reportAnalyzerService;

  private final WebReportPresenter webReportPresenter;

  @GetMapping(STATS_URL)
  @ResponseBody
  public ResponseEntity<ReportReponse> generate() {

    Report report = reportAnalyzerService.generateReport();

    ReportReponse reportReponse = webReportPresenter.generateReport(report);
    return new ResponseEntity<>(reportReponse, HttpStatus.OK);
  }

}
