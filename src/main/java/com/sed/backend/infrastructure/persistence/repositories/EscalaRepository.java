package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Escala;

import java.util.UUID;

public interface EscalaRepository extends JpaRepository<Escala, UUID> {
}