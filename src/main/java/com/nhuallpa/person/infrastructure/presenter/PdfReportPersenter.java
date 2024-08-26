package com.nhuallpa.person.infrastructure.presenter;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.infrastructure.response.ReportReponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component("PdfReportPresenter")
public class PdfReportPersenter implements ReportPresenter {

  private static final Logger logger = LoggerFactory.getLogger(PdfReportPersenter.class);
  public static final String TARGET = "pdf";

  public ReportReponse generateReport(Report report) {
    logger.info("Generate report in PDF format and drop in a CDN");
    return ReportReponse.builder()
            .target(TARGET)
            .trackingId(UUID.randomUUID())
            .destinationUrl("https://somewhere.mycompany.com/person-report.pdf")
            .build();
  }
}
