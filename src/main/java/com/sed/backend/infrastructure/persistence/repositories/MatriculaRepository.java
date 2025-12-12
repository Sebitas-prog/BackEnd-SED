package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.domain.enums.EstadoMatriculaEnum;

import java.util.List;
import java.util.UUID;

public interface MatriculaRepository extends JpaRepository<Matricula, UUID> {
    List<Matricula> findBySeccionId(UUID seccionId);

    List<Matricula> findByEstudianteId(UUID estudianteId);

    List<Matricula> findByEstado(EstadoMatriculaEnum estado);
}