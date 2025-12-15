package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Seccion;

import java.util.List;
import java.util.UUID;

public interface SeccionRepository extends JpaRepository<Seccion, UUID> {
    List<Seccion> findByPeriodoId(UUID periodoId);

    List<Seccion> findByDocenteId(UUID docenteId);

    List<Seccion> findByDocenteIdAndPeriodoId(UUID docenteId, UUID periodoId);

    List<Seccion> findByCursoIdAndPeriodoId(UUID cursoId, UUID periodoId);

    void deleteByPeriodoId(UUID periodoId);

    List<Seccion> findByInstrumento_Id(UUID instrumentoId);

    void deleteByInstrumento_Id(UUID instrumentoId);
}
