package com.sed.backend.presentation.controllers.docente;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints para que un docente consulte sus resultados de evaluación.
 */
@RestController
@RequestMapping("/api/docentes")
public class ResultadosDocenteController {

    @GetMapping("/{id}/resultados")
    public ResponseEntity<ApiResponse<Void>> obtenerResultados(@PathVariable("id") String docenteId) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Resultados del docente %s pendientes de implementación".formatted(docenteId))
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}