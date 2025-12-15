package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.evaluacion.MetricasPeriodoResponse;
import com.sed.backend.domain.enums.EstadoEvaluacionPendienteEnum;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionPendienteRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMetricasPeriodoUseCase {

    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;
    private final EvaluacionPendienteRepository evaluacionPendienteRepository;

    @Transactional(readOnly = true)
    public MetricasPeriodoResponse execute(UUID periodoId) {
        var secciones = seccionRepository.findByPeriodoId(periodoId);
        long docentesEvaluados = secciones.stream()
                .filter(s -> s.getInstrumento() != null && s.getDocente() != null)
                .map(s -> s.getDocente().getId())
                .distinct()
                .count();

        long estudiantesMatriculados = secciones.stream()
                .mapToLong(s -> matriculaRepository.countBySeccionId(s.getId()))
                .sum();

        long pendientes = secciones.stream()
                .mapToLong(s -> evaluacionPendienteRepository.countBySeccion_IdAndEstado(
                        s.getId(), EstadoEvaluacionPendienteEnum.PENDIENTE))
                .sum();

        long completadas = secciones.stream()
                .mapToLong(s -> evaluacionPendienteRepository.countBySeccion_IdAndEstado(
                        s.getId(), EstadoEvaluacionPendienteEnum.COMPLETADA))
                .sum();

        double tasaRespuesta = (pendientes + completadas) == 0
                ? 0
                : (completadas * 100.0 / (pendientes + completadas));
        double progreso = tasaRespuesta;

        return MetricasPeriodoResponse.builder()
                .docentesEvaluados(docentesEvaluados)
                .estudiantesMatriculados(estudiantesMatriculados)
                .pendientes(pendientes)
                .completadas(completadas)
                .tasaRespuesta(tasaRespuesta)
                .progreso(progreso)
                .build();
    }
}
