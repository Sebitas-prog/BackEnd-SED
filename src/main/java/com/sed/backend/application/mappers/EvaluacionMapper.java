package com.sed.backend.application.mappers;

import com.sed.backend.application.dto.response.evaluacion.EvaluacionResponse;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;

public final class EvaluacionMapper {

    private EvaluacionMapper() {
    }

    public static EvaluacionResponse toResponse(Evaluacion evaluacion) {
        return EvaluacionResponse.builder()
                .id(evaluacion.getId())
                .periodoId(evaluacion.getPeriodo().getId())
                .matriculaId(evaluacion.getMatricula().getId())
                .estado(evaluacion.getEstado())
                .creadoEn(evaluacion.getCreadoEn())
                .build();
    }
}