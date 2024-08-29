package com.nhuallpa.person.infrastructure.controller;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.infrastructure.response.ReportReponse;

public interface ReportPresenter {

  ReportReponse generateReport(Report data);

}
