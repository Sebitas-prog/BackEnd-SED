package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class InstrumentoCreateRequest {

    @NotBlank
    @Size(max = 120)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    private UUID periodoId;

    @Valid
    @Size(min = 1, message = "Debe registrar al menos un módulo con preguntas")
    private List<ModuloCreateRequest> modulos;
}
