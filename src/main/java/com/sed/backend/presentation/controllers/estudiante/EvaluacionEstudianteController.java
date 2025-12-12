package com.sed.backend.presentation.controllers.estudiante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints para que los estudiantes registren evaluaciones o respuestas.
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EvaluacionEstudianteController {

    @PostMapping("/{id}/evaluaciones")
    public ResponseEntity<ApiResponse<Void>> enviarEvaluacion(@PathVariable("id") String estudianteId) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Recepción de evaluación para el estudiante %s pendiente de implementación"
                        .formatted(estudianteId))
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}