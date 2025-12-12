package com.sed.backend.application.dto.response.evaluacion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstadisticasResponse {
    private final long totalEvaluaciones;
    private final double promedioGeneral;
    private final long evaluacionesAprobatorias;
}