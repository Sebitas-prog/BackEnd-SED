package com.sed.backend.application.services.evaluacion;

import com.sed.backend.domain.entities.evaluacion.Evaluacion;
import com.sed.backend.domain.enums.EstadoEvaluacionEnum;

import java.util.List;
import java.util.UUID;

public interface EvaluacionService {
    Evaluacion crearEvaluacion(Evaluacion evaluacion);

    List<Evaluacion> listarPorPeriodo(UUID periodoId, int page, int size);

    Evaluacion actualizarEstado(UUID evaluacionId, EstadoEvaluacionEnum estado);
}