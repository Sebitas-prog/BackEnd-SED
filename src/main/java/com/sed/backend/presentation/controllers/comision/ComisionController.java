package com.sed.backend.presentation.controllers.comision;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints para la comisión encargada de gestionar el proceso de evaluación.
 */
@RestController
@RequestMapping("/api/comision")
public class ComisionController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarMiembros() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de la comisión pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> crearComision() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Creación de comisión pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}