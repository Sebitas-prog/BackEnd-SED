package com.sed.backend.application.dto.response.evaluacion;

import lombok.Builder;
import lombok.Getter;
import com.sed.backend.domain.enums.EstadoEvaluacionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class EvaluacionResponse {
    private final UUID id;
    private final UUID periodoId;
    private final UUID matriculaId;
    private final EstadoEvaluacionEnum estado;
    private final LocalDateTime creadoEn;
}