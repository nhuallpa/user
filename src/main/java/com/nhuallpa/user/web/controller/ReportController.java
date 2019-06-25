package com.nhuallpa.user.web.controller;

import com.nhuallpa.user.model.Report;
import com.nhuallpa.user.web.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/estadisticas")
public class ReportController {


  @Autowired
  private ReportService reportService;

  @GetMapping()
  @ResponseBody  public ResponseEntity<Report> getReport() {
    return new ResponseEntity<>(reportService.generateReport(), HttpStatus.OK);
  }

}
