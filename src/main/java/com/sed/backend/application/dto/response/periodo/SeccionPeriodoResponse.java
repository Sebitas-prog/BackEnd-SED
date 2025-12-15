package com.sed.backend.application.dto.response.periodo;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SeccionPeriodoResponse {
    private UUID seccionId;
    private UUID cursoId;
    private String cursoNombre;
    private UUID periodoId;
    private String periodoNombre;
    private String codigoSeccion;
    private String modalidad;
    private UUID docenteId;
    private String docenteNombre;
    private long matriculados;
    private long evaluaciones;
    private UUID instrumentoId;
}
