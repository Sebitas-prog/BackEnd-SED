package com.sed.backend.application.dto.response.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponse {
    private String titulo;
    private Map<String, Object> datos;
    private String generadoPor;
}