package com.nhuallpa.person.infrastructure.presenter;

import com.nhuallpa.person.domain.model.Report;
import com.nhuallpa.person.infrastructure.response.ReportReponse;

public interface ReportPresenter {

  ReportReponse generateReport(Report report);

}
