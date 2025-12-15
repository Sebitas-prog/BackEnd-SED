package com.sed.backend.application.dto.response.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaResponse {
    private UUID id;
    private String enunciado;
}
