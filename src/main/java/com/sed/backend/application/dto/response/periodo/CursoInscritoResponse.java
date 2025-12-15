package com.sed.backend.application.dto.response.periodo;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CursoInscritoResponse {
    private UUID matriculaId;
    private UUID seccionId;
    private UUID cursoId;
    private String cursoCodigo;
    private String cursoNombre;
    private UUID periodoId;
    private String periodoNombre;
    private String codigoSeccion;
    private String modalidad;
    private UUID docenteId;
    private String docenteNombre;
    private UUID instrumentoId;
    private boolean evaluado;
}
