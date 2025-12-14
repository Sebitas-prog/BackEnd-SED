package com.sed.backend.application.dto.response.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasResponse {
    private long totalEvaluaciones;
    private double promedioGeneral;
    private long evaluacionesAprobatorias;
}