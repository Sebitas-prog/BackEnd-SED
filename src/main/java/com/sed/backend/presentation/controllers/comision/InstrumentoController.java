package com.sed.backend.presentation.controllers.comision;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints para gestionar instrumentos y preguntas de evaluación.
 */
@RestController
@RequestMapping("/api/instrumentos")
public class InstrumentoController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarInstrumentos() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de instrumentos pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> crearInstrumento() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Creación de instrumento pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}