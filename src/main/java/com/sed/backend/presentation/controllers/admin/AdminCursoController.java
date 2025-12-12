package com.sed.backend.presentation.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints de administración de cursos.
 */
@RestController
@RequestMapping("/api/admin/cursos")
public class AdminCursoController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarCursos() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de cursos pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> crearCurso() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Creación de curso pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}