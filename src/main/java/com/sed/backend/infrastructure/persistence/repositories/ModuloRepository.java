package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Modulo;

import java.util.UUID;

public interface ModuloRepository extends JpaRepository<Modulo, UUID> {
}