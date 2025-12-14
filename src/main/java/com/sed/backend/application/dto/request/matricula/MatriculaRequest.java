package com.sed.backend.application.dto.request.matricula;

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
public class MatriculaRequest {

    @NotNull
    private UUID estudianteId;

    @NotNull
    private UUID seccionId;
}