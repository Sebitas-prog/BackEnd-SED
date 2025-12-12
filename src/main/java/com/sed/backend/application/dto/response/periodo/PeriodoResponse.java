package com.sed.backend.application.dto.response.periodo;

import lombok.Builder;
import lombok.Getter;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class PeriodoResponse {
    private final UUID id;
    private final String nombre;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final EstadoPeriodoEnum estado;
}