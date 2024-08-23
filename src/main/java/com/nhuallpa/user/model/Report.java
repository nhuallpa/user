package com.nhuallpa.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Report {

    private String target;

    private Long totalMale;

    private Long totalFemale;

    private Long argentinePercentage;
}
