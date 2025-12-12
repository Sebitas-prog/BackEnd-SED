package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.evaluacion.EstadisticasResponse;
import com.sed.backend.infrastructure.implementations.EstadisticasServiceImpl;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalculateEstadisticasUseCase {

    private final EstadisticasServiceImpl estadisticasService;

    @Transactional(readOnly = true)
    public EstadisticasResponse execute() {
        Map<String, Long> totales = estadisticasService.obtenerTotales();
        long totalEvaluaciones = totales.getOrDefault("totalEvaluaciones", 0L);
        long totalRespuestas = totales.getOrDefault("totalRespuestas", 0L);

        double promedio = totalEvaluaciones == 0 ? 0 : (double) totalRespuestas / totalEvaluaciones;

        return EstadisticasResponse.builder()
                .totalEvaluaciones(totalEvaluaciones)
                .promedioGeneral(promedio)
                .evaluacionesAprobatorias(totalEvaluaciones)
                .build();
    }
}