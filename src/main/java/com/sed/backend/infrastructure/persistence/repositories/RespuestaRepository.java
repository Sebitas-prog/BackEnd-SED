package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Respuesta;

import java.util.List;
import java.util.UUID;

public interface RespuestaRepository extends JpaRepository<Respuesta, UUID> {
    List<Respuesta> findByEvaluacionId(UUID evaluacionId);
}