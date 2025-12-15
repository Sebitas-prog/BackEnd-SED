package com.sed.backend.presentation.controllers.periodo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.request.periodo.PeriodoRequest;
import com.sed.backend.application.dto.request.periodo.PeriodoUpdateRequest;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.usecases.periodo.CreatePeriodoUseCase;
import com.sed.backend.application.usecases.periodo.GetPeriodosActivosUseCase;
import com.sed.backend.application.usecases.periodo.GetPeriodosUseCase;
import com.sed.backend.application.usecases.periodo.UpdatePeriodoUseCase;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/periodos")
@RequiredArgsConstructor
public class PeriodoController {

    private final CreatePeriodoUseCase createPeriodoUseCase;
    private final GetPeriodosActivosUseCase getPeriodosActivosUseCase;
    private final GetPeriodosUseCase getPeriodosUseCase;
    private final UpdatePeriodoUseCase updatePeriodoUseCase;
    private final PeriodoServiceImpl periodoService;

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

    @GetMapping
    public ResponseEntity<ApiResponse<List<PeriodoResponse>>> listarTodos() {
        List<PeriodoResponse> periodos = getPeriodosUseCase.execute();
        ApiResponse<List<PeriodoResponse>> body = ApiResponse.<List<PeriodoResponse>>builder()
                .success(true)
                .message("Lista de periodos")
                .data(periodos)
                .build();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{periodoId}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable UUID periodoId) {
        periodoService.eliminar(periodoId);
        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .success(true)
                .message("Periodo eliminado")
                .data(null)
                .build();
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{periodoId}")
    public ResponseEntity<ApiResponse<PeriodoResponse>> actualizar(
            @Valid @RequestBody PeriodoUpdateRequest request,
            @PathVariable UUID periodoId) {
        PeriodoResponse actualizado = updatePeriodoUseCase.execute(periodoId, request);
        ApiResponse<PeriodoResponse> body = ApiResponse.<PeriodoResponse>builder()
                .success(true)
                .message("Periodo actualizado")
                .data(actualizado)
                .build();
        return ResponseEntity.ok(body);
    }
}
