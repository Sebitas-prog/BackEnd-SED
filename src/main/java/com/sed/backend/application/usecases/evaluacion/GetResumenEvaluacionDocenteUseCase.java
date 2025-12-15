package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.evaluacion.ResumenEvaluacionResponse;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;
import com.sed.backend.domain.entities.evaluacion.Respuesta;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetResumenEvaluacionDocenteUseCase {

    private final EvaluacionRepository evaluacionRepository;

    @Transactional(readOnly = true)
    public ResumenEvaluacionResponse execute(UUID seccionId) {
        List<Evaluacion> evaluaciones = evaluacionRepository.findBySeccion_Id(seccionId);
        if (evaluaciones.isEmpty()) {
            return ResumenEvaluacionResponse.builder()
                    .promedios(List.of())
                    .respuestas(List.of())
                    .promedioGeneral(0)
                    .totalRespuestas(0)
                    .build();
        }

        Map<String, List<Integer>> calificacionesPorModulo = new HashMap<>();
        List<ResumenEvaluacionResponse.RespuestaDetalle> detalles = new ArrayList<>();

        for (Evaluacion ev : evaluaciones) {
            for (Respuesta r : ev.getRespuestas()) {
                String modulo = r.getPregunta().getModulo().getNombre();
                calificacionesPorModulo.computeIfAbsent(modulo, k -> new ArrayList<>())
                        .add(Optional.ofNullable(r.getValorCalificacion()).orElse(0));
            }

            // Detalle anónimo por evaluación (promedio de esa evaluación)
            double promedioEval = ev.getRespuestas().stream()
                    .mapToInt(resp -> Optional.ofNullable(resp.getValorCalificacion()).orElse(0))
                    .average()
                    .orElse(0);

            String estudianteAnon = "Estudiante Anónimo";
            detalles.add(ResumenEvaluacionResponse.RespuestaDetalle.builder()
                    .fecha(ev.getCompletadoEn())
                    .calificacion((int) Math.round(promedioEval))
                    .comentario(
                            ev.getRespuestas().stream()
                                    .map(Respuesta::getComentario)
                                    .filter(Objects::nonNull)
                                    .filter(s -> !s.isBlank())
                                    .findFirst()
                                    .orElse(""))
                    .estudiante(estudianteAnon)
                    .build());
        }

        List<ResumenEvaluacionResponse.PromedioModulo> promedios = calificacionesPorModulo.entrySet().stream()
                .map(e -> ResumenEvaluacionResponse.PromedioModulo.builder()
                        .modulo(e.getKey())
                        .promedio(e.getValue().stream().mapToInt(Integer::intValue).average().orElse(0))
                        .build())
                .collect(Collectors.toList());

        double promedioGeneral = promedios.stream()
                .mapToDouble(ResumenEvaluacionResponse.PromedioModulo::getPromedio)
                .average()
                .orElse(0);

        return ResumenEvaluacionResponse.builder()
                .promedios(promedios)
                .respuestas(detalles)
                .promedioGeneral(promedioGeneral)
                .totalRespuestas(detalles.size())
                .build();
    }
}
