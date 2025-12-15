package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;

import java.util.UUID;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, UUID>, JpaSpecificationExecutor<Evaluacion> {
    boolean existsByMatriculaIdAndSeccionId(UUID matriculaId, UUID seccionId);

    long countBySeccionId(UUID seccionId);

    java.util.List<Evaluacion> findBySeccion_Id(UUID seccionId);
}
