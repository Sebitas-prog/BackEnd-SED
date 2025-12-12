package com.sed.backend.presentation.controllers.docente;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints de consulta para docentes.
 */
@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarDocentes() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de docentes pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> obtenerDetalle(@PathVariable("id") String id) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Detalle de docente %s pendiente de implementación".formatted(id))
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}