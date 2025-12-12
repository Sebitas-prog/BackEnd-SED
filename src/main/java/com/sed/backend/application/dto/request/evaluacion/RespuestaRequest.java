package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RespuestaRequest {

    @NotNull
    private UUID preguntaId;

    @Min(1)
    @Max(5)
    private int valor;

    private String comentario;
}