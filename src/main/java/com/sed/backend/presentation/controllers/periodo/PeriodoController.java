package com.sed.backend.presentation.controllers.periodo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.request.periodo.PeriodoRequest;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.usecases.periodo.CreatePeriodoUseCase;
import com.sed.backend.application.usecases.periodo.GetPeriodosActivosUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/periodos")
@RequiredArgsConstructor
public class PeriodoController {

    private final CreatePeriodoUseCase createPeriodoUseCase;
    private final GetPeriodosActivosUseCase getPeriodosActivosUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PeriodoResponse>> crear(@Valid @RequestBody PeriodoRequest request) {
        PeriodoResponse periodo = createPeriodoUseCase.execute(request);
        ApiResponse<PeriodoResponse> body = ApiResponse.<PeriodoResponse>builder()
                .success(true)
                .message("Periodo creado")
                .data(periodo)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<PeriodoResponse>>> activos() {
        List<PeriodoResponse> periodos = getPeriodosActivosUseCase.execute();
        ApiResponse<List<PeriodoResponse>> body = ApiResponse.<List<PeriodoResponse>>builder()
                .success(true)
                .message("Periodos activos")
                .data(periodos)
                .build();
        return ResponseEntity.ok(body);
    }
}