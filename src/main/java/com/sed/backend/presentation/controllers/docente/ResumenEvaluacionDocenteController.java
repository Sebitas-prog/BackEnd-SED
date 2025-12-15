package com.sed.backend.presentation.controllers.docente;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.evaluacion.ResumenEvaluacionResponse;
import com.sed.backend.application.usecases.evaluacion.GetResumenEvaluacionDocenteUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/api/docente/evaluaciones")
@RequiredArgsConstructor
public class ResumenEvaluacionDocenteController {

    private final GetResumenEvaluacionDocenteUseCase getResumenEvaluacionDocenteUseCase;

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<ResumenEvaluacionResponse>> resumenPorSeccion(
            @RequestParam UUID seccionId) {
        ResumenEvaluacionResponse resumen = getResumenEvaluacionDocenteUseCase.execute(seccionId);
        ApiResponse<ResumenEvaluacionResponse> body = ApiResponse.<ResumenEvaluacionResponse>builder()
                .success(true)
                .message("Resumen de evaluaciones por seccion")
                .data(resumen)
                .build();
        return ResponseEntity.ok(body);
    }
}
