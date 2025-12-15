package com.sed.backend.presentation.controllers.estudiante;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.periodo.CursoInscritoResponse;
import com.sed.backend.application.usecases.curso.GetCursosEstudianteUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estudiante")
@RequiredArgsConstructor
public class CursoEstudianteController {

    private final GetCursosEstudianteUseCase getCursosEstudianteUseCase;

    @GetMapping("/cursos")
    public ResponseEntity<ApiResponse<List<CursoInscritoResponse>>> cursosPorPeriodo(
            @RequestParam UUID estudianteId,
            @RequestParam UUID periodoId) {
        List<CursoInscritoResponse> cursos = getCursosEstudianteUseCase.execute(estudianteId, periodoId);
        ApiResponse<List<CursoInscritoResponse>> body = ApiResponse.<List<CursoInscritoResponse>>builder()
                .success(true)
                .message("Cursos del estudiante en el periodo")
                .data(cursos)
                .build();
        return ResponseEntity.ok(body);
    }
}
