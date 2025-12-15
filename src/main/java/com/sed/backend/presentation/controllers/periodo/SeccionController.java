package com.sed.backend.presentation.controllers.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.periodo.SeccionPeriodoResponse;
import com.sed.backend.application.usecases.seccion.GetSeccionesPorPeriodoUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comision/periodos")
@RequiredArgsConstructor
public class SeccionController {

    private final GetSeccionesPorPeriodoUseCase getSeccionesPorPeriodoUseCase;

    @GetMapping("/{periodoId}/secciones")
    public ResponseEntity<ApiResponse<List<SeccionPeriodoResponse>>> listarPorPeriodo(
            @PathVariable UUID periodoId) {
        List<SeccionPeriodoResponse> secciones = getSeccionesPorPeriodoUseCase.execute(periodoId);
        ApiResponse<List<SeccionPeriodoResponse>> body = ApiResponse.<List<SeccionPeriodoResponse>>builder()
                .success(true)
                .message("Secciones del periodo")
                .data(secciones)
                .build();
        return ResponseEntity.ok(body);
    }
}
