package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Instrumento;

import java.util.UUID;

public interface InstrumentoRepository extends JpaRepository<Instrumento, UUID> {
}