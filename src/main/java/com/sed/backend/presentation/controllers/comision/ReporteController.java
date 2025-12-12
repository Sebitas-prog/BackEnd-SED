package com.sed.backend.presentation.controllers.comision;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints para emisión de reportes y dashboards.
 */
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<Void>> obtenerResumen() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Resumen de reportes pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}