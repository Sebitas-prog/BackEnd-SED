package com.sed.backend.application.mappers;

import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.domain.entities.academico.Periodo;

public final class PeriodoMapper {

    private PeriodoMapper() {
    }

    public static PeriodoResponse toResponse(Periodo periodo) {
        return PeriodoResponse.builder()
                .id(periodo.getId())
                .nombre(periodo.getNombre())
                .fechaInicio(periodo.getRangoFechas().getFechaInicio())
                .fechaFin(periodo.getRangoFechas().getFechaFin())
                .estado(periodo.getEstado())
                .build();
    }
}