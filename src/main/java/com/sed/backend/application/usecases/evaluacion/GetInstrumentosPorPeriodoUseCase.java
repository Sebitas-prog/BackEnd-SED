package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.evaluacion.InstrumentoResponse;
import com.sed.backend.application.mappers.InstrumentoMapper;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetInstrumentosPorPeriodoUseCase {

    private final InstrumentoServiceImpl instrumentoService;
    private final PeriodoServiceImpl periodoService;

    @Transactional(readOnly = true)
    public List<InstrumentoResponse> execute(UUID periodoId) {
        periodoService.buscarPorId(periodoId);
        return instrumentoService.listarPorPeriodo(periodoId).stream()
                .map(InstrumentoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
