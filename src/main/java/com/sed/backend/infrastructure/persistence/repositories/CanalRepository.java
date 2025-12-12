package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Canal;

import java.util.Optional;
import java.util.UUID;

public interface CanalRepository extends JpaRepository<Canal, UUID> {
    Optional<Canal> findByCodigoIgnoreCase(String codigo);
}