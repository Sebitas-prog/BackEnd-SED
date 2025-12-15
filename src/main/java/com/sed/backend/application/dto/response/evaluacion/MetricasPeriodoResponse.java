package com.sed.backend.application.dto.response.evaluacion;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MetricasPeriodoResponse {
    private long docentesEvaluados;
    private long estudiantesMatriculados;
    private long pendientes;
    private long completadas;
    private double tasaRespuesta;
    private double progreso;
}
