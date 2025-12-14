package com.sed.backend.application.dto.response.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sed.backend.domain.enums.EstadoEvaluacionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionResponse {
    private UUID id;
    private UUID periodoId;
    private UUID matriculaId;
    private EstadoEvaluacionEnum estado;
    private LocalDateTime creadoEn;
}