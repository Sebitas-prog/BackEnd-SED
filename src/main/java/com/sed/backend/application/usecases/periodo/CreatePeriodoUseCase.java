package com.sed.backend.application.usecases.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.periodo.PeriodoRequest;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.mappers.PeriodoMapper;
import com.sed.backend.application.validators.PeriodoValidator;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.valueobjects.RangoFechas;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

@Service
@RequiredArgsConstructor
public class CreatePeriodoUseCase {

    private final PeriodoServiceImpl periodoService;
    private final PeriodoValidator periodoValidator;

    @Transactional
    public PeriodoResponse execute(PeriodoRequest request) {
        RangoFechas rango = periodoValidator.buildRange(
                request.getFechaInicio(), request.getFechaFin());

        Periodo periodo = periodoService.crearPeriodo(
                request.getNombre(), rango, request.getEstado());

        return PeriodoMapper.toResponse(periodo);
    }
}