package com.sed.backend.application.dto.request.evaluacion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuloCreateRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @Valid
    @Size(min = 1, message = "Debe agregar al menos una pregunta")
    private List<PreguntaCreateRequest> preguntas;
}
