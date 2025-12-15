package com.sed.backend.application.usecases.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.periodo.PeriodoUpdateRequest;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.mappers.PeriodoMapper;
import com.sed.backend.application.validators.PeriodoValidator;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.valueobjects.RangoFechas;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePeriodoUseCase {

    private final PeriodoServiceImpl periodoService;
    private final PeriodoValidator periodoValidator;

    @Transactional
    public PeriodoResponse execute(UUID periodoId, PeriodoUpdateRequest request) {
        RangoFechas rango = periodoValidator.buildRange(request.getFechaInicio(), request.getFechaFin());
        Periodo periodo = periodoService.actualizarPeriodo(periodoId, request.getNombre(), rango, request.getEstado());
        return PeriodoMapper.toResponse(periodo);
    }
}
