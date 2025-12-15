package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.academico.Estudiante;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.enums.EstadoMatriculaEnum;
import com.sed.backend.infrastructure.persistence.repositories.EstudianteRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionPendienteRepository;
import com.sed.backend.domain.entities.evaluacion.EvaluacionPendiente;
import com.sed.backend.domain.enums.EstadoEvaluacionPendienteEnum;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final SeccionRepository seccionRepository;
    private final EvaluacionPendienteRepository evaluacionPendienteRepository;

    @Transactional
    public Matricula matricular(UUID estudianteId, UUID seccionId) {
        if (matriculaRepository.existsByEstudianteIdAndSeccionId(estudianteId, seccionId)) {
            throw new IllegalArgumentException("El estudiante ya está matriculado en este curso");
        }
        long inscritos = matriculaRepository.countBySeccionId(seccionId);
        if (inscritos >= 30) {
            throw new IllegalArgumentException("El curso alcanzó el máximo de 30 estudiantes");
        }

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Seccion seccion = seccionRepository.findById(seccionId)
                .orElseThrow(() -> new IllegalArgumentException("Sección no encontrada"));

        Matricula matricula = Matricula.builder()
                .estudiante(estudiante)
                .seccion(seccion)
                .estado(EstadoMatriculaEnum.INSCRITO)
                .build();
        Matricula saved = matriculaRepository.save(matricula);

        // Si hay instrumento asignado a la seccion, crear pendiente de evaluacion
        if (seccion.getInstrumento() != null) {
            boolean existePendiente = evaluacionPendienteRepository
                    .existsByMatricula_IdAndInstrumento_IdAndEstado(
                            saved.getId(),
                            seccion.getInstrumento().getId(),
                            EstadoEvaluacionPendienteEnum.PENDIENTE);
            if (!existePendiente) {
                EvaluacionPendiente pendiente = EvaluacionPendiente.builder()
                        .matricula(saved)
                        .seccion(seccion)
                        .instrumento(seccion.getInstrumento())
                        .estado(EstadoEvaluacionPendienteEnum.PENDIENTE)
                        .build();
                evaluacionPendienteRepository.save(pendiente);
            }
        }

        return saved;
    }
}
