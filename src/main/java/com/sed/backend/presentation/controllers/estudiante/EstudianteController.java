package com.sed.backend.presentation.controllers.estudiante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints de gestión de estudiantes.
 * Esta capa de presentación deja listo el contrato REST para futuras
 * integraciones
 * con casos de uso especializados.
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarEstudiantes() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de estudiantes pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> obtenerDetalle(@PathVariable("id") String id) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Detalle de estudiante %s pendiente de implementación".formatted(id))
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}