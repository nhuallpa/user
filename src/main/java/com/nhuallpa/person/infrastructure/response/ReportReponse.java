package com.nhuallpa.person.infrastructure.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ReportReponse {

  private UUID trackingId;
  private String destinationUrl;
  private String target;

}
