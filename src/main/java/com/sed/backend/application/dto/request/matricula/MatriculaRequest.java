package com.sed.backend.application.dto.request.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MatriculaRequest {

    @NotNull
    private UUID estudianteId;

    @NotNull
    private UUID seccionId;
}