package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.InstrumentoEscala;

import java.util.List;
import java.util.UUID;

public interface InstrumentoEscalaRepository extends JpaRepository<InstrumentoEscala, UUID> {
    List<InstrumentoEscala> findByInstrumentoId(UUID instrumentoId);
}