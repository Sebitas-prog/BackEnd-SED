package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionRequest {

    @NotNull
    private UUID matriculaId;

    @NotNull
    private UUID instrumentoId;

    @Valid
    @Size(min = 1, message = "Debe registrar al menos una respuesta")
    private List<RespuestaRequest> respuestas;
}