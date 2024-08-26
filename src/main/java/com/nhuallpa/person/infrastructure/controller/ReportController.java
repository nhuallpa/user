package com.nhuallpa.person.infrastructure.controller;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.domain.service.ReportAnalyzerService;
import com.nhuallpa.person.infrastructure.presenter.ReportPresenter;
import com.nhuallpa.person.infrastructure.response.ReportReponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Controller
public class ReportController {

  public static final String STATS_URL = "/stats";

  public static final Map<String, String> reportProcesses = Map.of(
          "web", "WebReportPresenter",
          "pdf", "PdfReportPresenter"
  );
  @Autowired
  private BeanFactory beanFactory;

  @Autowired
  private ReportAnalyzerService reportAnalyzerService;

  @GetMapping(STATS_URL)
  @ResponseBody
  public ResponseEntity<ReportReponse> getReport(@RequestParam(name = "target") String target) {

    ReportPresenter service = selectPresenterProcess(target);
    Report report = reportAnalyzerService.generateReport();

    ReportReponse reportReponse = service.generateReport(report);
    return new ResponseEntity<>(reportReponse, HttpStatus.OK);
  }

  private ReportPresenter selectPresenterProcess(String target) {
    return Optional.of(
            beanFactory.getBean(reportProcesses.get(target.toLowerCase(Locale.ROOT)), ReportPresenter.class)
    ).orElseThrow(UnsupportedOperationException::new);
  }

}
