package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.auditoria.LogAccion;

import java.util.List;
import java.util.UUID;

public interface LogAccionRepository extends JpaRepository<LogAccion, UUID> {
    List<LogAccion> findByUsuarioIdOrderByCreadoEnDesc(UUID usuarioId);
}