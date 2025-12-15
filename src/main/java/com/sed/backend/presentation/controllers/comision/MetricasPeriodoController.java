package com.sed.backend.presentation.controllers.comision;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.evaluacion.MetricasPeriodoResponse;
import com.sed.backend.application.usecases.evaluacion.GetMetricasPeriodoUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/api/comision/periodos")
@RequiredArgsConstructor
public class MetricasPeriodoController {

    private final GetMetricasPeriodoUseCase getMetricasPeriodoUseCase;

    @GetMapping("/{periodoId}/metricas")
    public ResponseEntity<ApiResponse<MetricasPeriodoResponse>> metricas(
            @PathVariable UUID periodoId) {
        MetricasPeriodoResponse response = getMetricasPeriodoUseCase.execute(periodoId);
        ApiResponse<MetricasPeriodoResponse> body = ApiResponse.<MetricasPeriodoResponse>builder()
                .success(true)
                .message("Metricas del periodo")
                .data(response)
                .build();
        return ResponseEntity.ok(body);
    }
}
