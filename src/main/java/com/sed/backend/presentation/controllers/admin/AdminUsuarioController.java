package com.sed.backend.presentation.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;

/**
 * Endpoints de administración de usuarios.
 */
@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuarioController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> listarUsuarios() {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Listado de usuarios pendiente de implementación")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Void>> actualizarEstado(@PathVariable("id") String usuarioId) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Actualización de estado para usuario %s pendiente de implementación".formatted(usuarioId))
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }
}