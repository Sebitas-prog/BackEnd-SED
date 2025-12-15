package com.sed.backend.application.dto.request.evaluacion;

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
public class AsignarInstrumentoRequest {

    @NotNull
    private UUID instrumentoId;

    @NotNull
    private UUID seccionId;
}
