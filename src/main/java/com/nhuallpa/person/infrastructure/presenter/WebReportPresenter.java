package com.nhuallpa.person.infrastructure.presenter;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.infrastructure.controller.ReportPresenter;
import com.nhuallpa.person.infrastructure.response.ReportReponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("WebReportPresenter")
public class WebReportPresenter implements ReportPresenter {

  private static final Logger logger = LoggerFactory.getLogger(WebReportPresenter.class);
  public static final String target = "web";

  @Override
  public ReportReponse generateReport(Report data) {
    logger.info("Generate report in HTML and drop in a CDN");
    return ReportReponse.builder()
            .target("web")
            .trackingId(UUID.randomUUID())
            .destinationUrl("https://somewhere.mycompany.com/persons-report.htm")
            .build();
  }
}
