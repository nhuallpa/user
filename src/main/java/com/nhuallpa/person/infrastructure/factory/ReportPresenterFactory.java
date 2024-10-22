package com.nhuallpa.person.infrastructure.factory;

import com.nhuallpa.person.infrastructure.controller.ReportPresenter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
public class ReportPresenterFactory {

  public static final Map<String, String> reportProcesses = Map.of(
          "web", "WebReportPresenter",
          "pdf", "PdfReportPresenter"
  );
  @Autowired
  private BeanFactory beanFactory;


  public ReportPresenter getPresenter(String target) {
    return Optional.of(
            beanFactory.getBean(reportProcesses.get(target.toLowerCase(Locale.ROOT)), ReportPresenter.class)
    ).orElseThrow(UnsupportedOperationException::new);
  }



}
