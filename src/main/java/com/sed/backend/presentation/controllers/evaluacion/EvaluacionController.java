package com.sed.backend.presentation.controllers.evaluacion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.request.evaluacion.EvaluacionRequest;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.common.PageResponse;
import com.sed.backend.application.dto.response.evaluacion.EstadisticasResponse;
import com.sed.backend.application.dto.response.evaluacion.EvaluacionResponse;
import com.sed.backend.application.usecases.evaluacion.CalculateEstadisticasUseCase;
import com.sed.backend.application.usecases.evaluacion.CreateEvaluacionUseCase;
import com.sed.backend.application.usecases.evaluacion.GetEvaluacionesUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final CreateEvaluacionUseCase createEvaluacionUseCase;
    private final GetEvaluacionesUseCase getEvaluacionesUseCase;
    private final CalculateEstadisticasUseCase calculateEstadisticasUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<EvaluacionResponse>> crear(@Valid @RequestBody EvaluacionRequest request) {
        EvaluacionResponse evaluacion = createEvaluacionUseCase.execute(request);
        ApiResponse<EvaluacionResponse> body = ApiResponse.<EvaluacionResponse>builder()
                .success(true)
                .message("Evaluación registrada")
                .data(evaluacion)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EvaluacionResponse>>> listar(
            @RequestParam UUID periodoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<EvaluacionResponse> evaluaciones = getEvaluacionesUseCase.execute(periodoId, page, size);
        ApiResponse<PageResponse<EvaluacionResponse>> body = ApiResponse.<PageResponse<EvaluacionResponse>>builder()
                .success(true)
                .message("Evaluaciones encontradas")
                .data(evaluaciones)
                .build();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticasResponse>> estadisticas() {
        EstadisticasResponse response = calculateEstadisticasUseCase.execute();
        ApiResponse<EstadisticasResponse> body = ApiResponse.<EstadisticasResponse>builder()
                .success(true)
                .message("Estadísticas calculadas")
                .data(response)
                .build();
        return ResponseEntity.ok(body);
    }
}