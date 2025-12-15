package com.sed.backend.application.dto.response.evaluacion;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ResumenEvaluacionResponse {
    @Getter
    @Builder
    public static class PromedioModulo {
        private String modulo;
        private double promedio;
    }

    @Getter
    @Builder
    public static class RespuestaDetalle {
        private LocalDateTime fecha;
        private int calificacion;
        private String comentario;
        private String estudiante; // anónimo
    }

    private List<PromedioModulo> promedios;
    private List<RespuestaDetalle> respuestas;
    private double promedioGeneral;
    private long totalRespuestas;
}
