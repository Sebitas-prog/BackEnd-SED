package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Pregunta;

import java.util.UUID;

public interface PreguntaRepository extends JpaRepository<Pregunta, UUID> {
}