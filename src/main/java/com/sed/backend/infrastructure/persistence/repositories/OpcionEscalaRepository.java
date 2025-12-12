package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.OpcionEscala;

import java.util.List;
import java.util.UUID;

public interface OpcionEscalaRepository extends JpaRepository<OpcionEscala, UUID> {
    List<OpcionEscala> findByEscalaId(UUID escalaId);
}