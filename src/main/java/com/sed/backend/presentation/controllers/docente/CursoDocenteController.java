package com.sed.backend.presentation.controllers.docente;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.periodo.CursoInscritoResponse;
import com.sed.backend.application.usecases.curso.GetCursosDocenteUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/docente")
@RequiredArgsConstructor
public class CursoDocenteController {

    private final GetCursosDocenteUseCase getCursosDocenteUseCase;

    @GetMapping("/cursos")
    public ResponseEntity<ApiResponse<List<CursoInscritoResponse>>> cursosPorPeriodo(
            @RequestParam UUID docenteId,
            @RequestParam UUID periodoId) {
        List<CursoInscritoResponse> cursos = getCursosDocenteUseCase.execute(docenteId, periodoId);
        ApiResponse<List<CursoInscritoResponse>> body = ApiResponse.<List<CursoInscritoResponse>>builder()
                .success(true)
                .message("Cursos del docente en el periodo")
                .data(cursos)
                .build();
        return ResponseEntity.ok(body);
    }
}
