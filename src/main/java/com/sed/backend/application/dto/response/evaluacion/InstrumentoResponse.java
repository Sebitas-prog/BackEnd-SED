package com.sed.backend.application.dto.response.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentoResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private UUID periodoId;
    private boolean activo;
    private List<ModuloResponse> modulos;
}
