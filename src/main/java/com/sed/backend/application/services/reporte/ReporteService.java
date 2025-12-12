
package com.sed.backend.application.services.reporte;

import java.util.Map;
import java.util.UUID;

public interface ReporteService {
    Map<String, Object> generarReporteDocente(UUID docenteId);
}