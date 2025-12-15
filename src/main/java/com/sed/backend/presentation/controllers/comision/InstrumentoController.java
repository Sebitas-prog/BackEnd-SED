package com.sed.backend.presentation.controllers.comision;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.request.evaluacion.InstrumentoCreateRequest;
import com.sed.backend.application.dto.request.evaluacion.AsignarInstrumentoRequest;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.evaluacion.InstrumentoResponse;
import com.sed.backend.application.usecases.evaluacion.CreateInstrumentoUseCase;
import com.sed.backend.application.usecases.evaluacion.GetInstrumentosPorPeriodoUseCase;
import com.sed.backend.application.usecases.evaluacion.AsignarInstrumentoASeccionUseCase;
import com.sed.backend.application.usecases.evaluacion.GetInstrumentoUseCase;
import com.sed.backend.application.usecases.evaluacion.UpdateInstrumentoUseCase;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;

import java.util.List;
import java.util.UUID;

/**
 * Endpoints para gestionar instrumentos y preguntas de evaluacion.
 */
@RestController
@RequestMapping("/api/instrumentos")
@RequiredArgsConstructor
public class InstrumentoController {

    private final CreateInstrumentoUseCase createInstrumentoUseCase;
    private final GetInstrumentosPorPeriodoUseCase getInstrumentosPorPeriodoUseCase;
    private final AsignarInstrumentoASeccionUseCase asignarInstrumentoASeccionUseCase;
    private final GetInstrumentoUseCase getInstrumentoUseCase;
    private final UpdateInstrumentoUseCase updateInstrumentoUseCase;
    private final InstrumentoServiceImpl instrumentoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InstrumentoResponse>>> listarInstrumentos(
            @RequestParam UUID periodoId) {
        List<InstrumentoResponse> instrumentos = getInstrumentosPorPeriodoUseCase.execute(periodoId);
        ApiResponse<List<InstrumentoResponse>> response = ApiResponse.<List<InstrumentoResponse>>builder()
                .success(true)
                .message("Instrumentos encontrados")
                .data(instrumentos)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{instrumentoId}")
    public ResponseEntity<ApiResponse<InstrumentoResponse>> obtenerInstrumento(
            @org.springframework.web.bind.annotation.PathVariable UUID instrumentoId) {
        InstrumentoResponse instrumento = getInstrumentoUseCase.execute(instrumentoId);
        ApiResponse<InstrumentoResponse> response = ApiResponse.<InstrumentoResponse>builder()
                .success(true)
                .message("Instrumento encontrado")
                .data(instrumento)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InstrumentoResponse>> crearInstrumento(
            @Valid @RequestBody InstrumentoCreateRequest request) {
        InstrumentoResponse response = createInstrumentoUseCase.execute(request);
        ApiResponse<InstrumentoResponse> body = ApiResponse.<InstrumentoResponse>builder()
                .success(true)
                .message("Instrumento creado")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{instrumentoId}")
    public ResponseEntity<ApiResponse<InstrumentoResponse>> actualizarInstrumento(
            @org.springframework.web.bind.annotation.PathVariable UUID instrumentoId,
            @Valid @RequestBody InstrumentoCreateRequest request) {
        InstrumentoResponse response = updateInstrumentoUseCase.execute(instrumentoId, request);
        ApiResponse<InstrumentoResponse> body = ApiResponse.<InstrumentoResponse>builder()
                .success(true)
                .message("Instrumento actualizado")
                .data(response)
                .build();
        return ResponseEntity.ok(body);
    }

    @PostMapping("/asignar")
    public ResponseEntity<ApiResponse<Void>> asignarASeccion(
            @Valid @RequestBody AsignarInstrumentoRequest request) {
        asignarInstrumentoASeccionUseCase.execute(request);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Instrumento asignado al curso")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{instrumentoId}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @org.springframework.web.bind.annotation.PathVariable UUID instrumentoId) {
        instrumentoService.eliminar(instrumentoId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Instrumento eliminado")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
