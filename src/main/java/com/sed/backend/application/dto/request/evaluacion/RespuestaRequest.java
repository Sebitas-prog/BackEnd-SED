package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaRequest {

    @NotNull
    private UUID preguntaId;

    @Min(1)
    @Max(5)
    private int valor;

    private String comentario;
}