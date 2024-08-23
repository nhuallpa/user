package com.nhuallpa.user.web.controller;

import com.nhuallpa.user.model.Report;
import com.nhuallpa.user.web.service.ReportService;

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

@Controller
public class ReportController {

  public static final String STATS_URL = "/stats";

  public static final Map<String, String> reportProcesses = Map.of(
          "web", "WebReportService",
          "pdf", "PdfReportService"
  );
  @Autowired
  private BeanFactory beanFactory;

  @GetMapping(STATS_URL)
  @ResponseBody
  public ResponseEntity<Report> getReport(@RequestParam(name = "target") String target) {

    ReportService service = selectService(target);

    return new ResponseEntity<>(service.generateReport(), HttpStatus.OK);
  }

  private ReportService selectService(String target) {
    return beanFactory.getBean(reportProcesses.get(target.toLowerCase(Locale.ROOT)), ReportService.class);
  }

}
