package com.sed.backend.application.services.evaluacion;

import java.util.Map;
import java.util.UUID;

public interface EstadisticasService {
    Map<String, Object> calcularResumen(UUID periodoId);
}