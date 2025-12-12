package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentoRequest {

    @NotBlank
    @Size(max = 120)
    private String nombre;

    @Size(max = 500)
    private String descripcion;
}