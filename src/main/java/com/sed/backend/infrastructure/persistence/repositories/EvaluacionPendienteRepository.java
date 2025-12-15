package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.EvaluacionPendiente;
import com.sed.backend.domain.enums.EstadoEvaluacionPendienteEnum;

import java.util.List;
import java.util.UUID;

public interface EvaluacionPendienteRepository extends JpaRepository<EvaluacionPendiente, UUID> {

    boolean existsByMatricula_IdAndInstrumento_IdAndEstado(UUID matriculaId, UUID instrumentoId, EstadoEvaluacionPendienteEnum estado);

    EvaluacionPendiente findFirstByMatricula_IdAndInstrumento_Id(UUID matriculaId, UUID instrumentoId);

    long countBySeccion_Id(UUID seccionId);

    long countBySeccion_IdAndEstado(UUID seccionId, EstadoEvaluacionPendienteEnum estado);

    long countByInstrumento_IdAndEstado(UUID instrumentoId, EstadoEvaluacionPendienteEnum estado);

    List<EvaluacionPendiente> findBySeccion_Id(UUID seccionId);
}
