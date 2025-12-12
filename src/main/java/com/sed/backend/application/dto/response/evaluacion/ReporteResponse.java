package com.sed.backend.application.dto.response.evaluacion;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ReporteResponse {
    private final String titulo;
    private final Map<String, Object> datos;
    private final String generadoPor;
}