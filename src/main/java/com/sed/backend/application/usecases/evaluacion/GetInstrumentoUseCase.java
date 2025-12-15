package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.evaluacion.InstrumentoResponse;
import com.sed.backend.application.mappers.InstrumentoMapper;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetInstrumentoUseCase {

    private final InstrumentoServiceImpl instrumentoService;

    @Transactional(readOnly = true)
    public InstrumentoResponse execute(UUID instrumentoId) {
        var instrumento = instrumentoService.obtenerPorId(instrumentoId);
        return InstrumentoMapper.toResponse(instrumento);
    }
}
