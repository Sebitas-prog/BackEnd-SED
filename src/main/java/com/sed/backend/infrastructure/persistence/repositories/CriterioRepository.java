package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Criterio;

import java.util.UUID;

public interface CriterioRepository extends JpaRepository<Criterio, UUID> {
}