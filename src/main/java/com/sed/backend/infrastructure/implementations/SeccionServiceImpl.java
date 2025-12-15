package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.academico.Curso;
import com.sed.backend.domain.entities.academico.Docente;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.enums.ModalidadSeccionEnum;
import com.sed.backend.infrastructure.persistence.repositories.CursoRepository;
import com.sed.backend.infrastructure.persistence.repositories.DocenteRepository;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionPendienteRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;
import com.sed.backend.infrastructure.persistence.repositories.PeriodoRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;
import com.sed.backend.domain.entities.evaluacion.EvaluacionPendiente;
import com.sed.backend.domain.enums.EstadoEvaluacionPendienteEnum;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeccionServiceImpl {

    private final SeccionRepository seccionRepository;
    private final CursoRepository cursoRepository;
    private final PeriodoRepository periodoRepository;
    private final DocenteRepository docenteRepository;
    private final MatriculaRepository matriculaRepository;
    private final EvaluacionPendienteRepository evaluacionPendienteRepository;

    @Transactional
    public Seccion crearOAsignar(UUID cursoId, UUID periodoId, UUID docenteId, String codigo, ModalidadSeccionEnum modalidad) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Periodo periodo = periodoRepository.findById(periodoId)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));

        Seccion existente = seccionRepository.findByCursoIdAndPeriodoId(cursoId, periodoId)
                .stream()
                .findFirst()
                .orElse(null);

        if (existente != null) {
            if (docenteId != null) {
                if (existente.getDocente() != null && !existente.getDocente().getId().equals(docenteId)) {
                    throw new IllegalArgumentException("Ya existe un docente asignado a este curso en el periodo");
                }
                Docente docente = docenteRepository.findById(docenteId)
                        .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
                existente.setDocente(docente);
            }
            return seccionRepository.save(existente);
        }

        Docente docente = null;
        if (docenteId != null) {
            docente = docenteRepository.findById(docenteId)
                    .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
        }

        Seccion seccion = Seccion.builder()
                .codigo(codigo != null ? codigo : "A")
                .modalidad(modalidad != null ? modalidad : ModalidadSeccionEnum.PRESENCIAL)
                .curso(curso)
                .periodo(periodo)
                .docente(docente)
                .build();
        return seccionRepository.save(seccion);
    }

    @Transactional
    public void crearPendientesParaSeccion(Seccion seccion, UUID instrumentoId) {
        if (seccion == null || instrumentoId == null) return;
        var matriculas = matriculaRepository.findBySeccionId(seccion.getId());
        matriculas.forEach(m -> {
            boolean existe = evaluacionPendienteRepository.existsByMatricula_IdAndInstrumento_IdAndEstado(
                    m.getId(), instrumentoId, EstadoEvaluacionPendienteEnum.PENDIENTE);
            if (!existe) {
                EvaluacionPendiente pendiente = EvaluacionPendiente.builder()
                        .matricula(m)
                        .seccion(seccion)
                        .instrumento(seccion.getInstrumento())
                        .estado(EstadoEvaluacionPendienteEnum.PENDIENTE)
                        .build();
                evaluacionPendienteRepository.save(pendiente);
            }
        });
    }
}
