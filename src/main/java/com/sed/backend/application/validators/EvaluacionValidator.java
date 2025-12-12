package com.sed.backend.application.validators;

import org.springframework.stereotype.Component;
import com.sed.backend.application.dto.request.evaluacion.EvaluacionRequest;

@Component
public class EvaluacionValidator {

    public void validate(EvaluacionRequest request) {
        if (request.getRespuestas() == null || request.getRespuestas().isEmpty()) {
            throw new IllegalArgumentException("Debe registrar al menos una respuesta");
        }
    }
}