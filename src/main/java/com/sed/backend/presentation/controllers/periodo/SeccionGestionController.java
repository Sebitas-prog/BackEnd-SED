package com.sed.backend.presentation.controllers.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.enums.ModalidadSeccionEnum;
import com.sed.backend.infrastructure.implementations.SeccionServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/secciones")
@RequiredArgsConstructor
public class SeccionGestionController {

    private final SeccionServiceImpl seccionService;

    public static record CrearSeccionRequest(UUID cursoId, UUID periodoId, UUID docenteId, String codigo, String modalidad) {}

    @PostMapping
    public ResponseEntity<ApiResponse<Seccion>> crear(@RequestBody CrearSeccionRequest request) {
        ModalidadSeccionEnum modalidad = request.modalidad() != null
                ? ModalidadSeccionEnum.valueOf(request.modalidad().toUpperCase())
                : ModalidadSeccionEnum.PRESENCIAL;

        Seccion seccion = seccionService.crearOAsignar(
                request.cursoId(),
                request.periodoId(),
                request.docenteId(),
                request.codigo(),
                modalidad);

        ApiResponse<Seccion> body = ApiResponse.<Seccion>builder()
                .success(true)
                .message("Seccion creada/asignada")
                .data(seccion)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
