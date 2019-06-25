package com.nhuallpa.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Report {

    @JsonProperty("cantidad_hombres")
    private Long totalMale;

    @JsonProperty("cantidad_mujeres")
    private Long totalFemale;

    @JsonProperty("porcentaje_argentinos")
    private Long argentinePercentage;
}
