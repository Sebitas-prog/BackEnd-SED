package com.sed.backend.presentation.controllers.matricula;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.infrastructure.implementations.MatriculaServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaServiceImpl matriculaService;

    public static record MatriculaRequest(UUID estudianteId, UUID seccionId) {}

    @PostMapping
    public ResponseEntity<ApiResponse<Matricula>> crear(@RequestBody MatriculaRequest request) {
        Matricula matricula = matriculaService.matricular(request.estudianteId(), request.seccionId());
        ApiResponse<Matricula> body = ApiResponse.<Matricula>builder()
                .success(true)
                .message("Matricula registrada")
                .data(matricula)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
