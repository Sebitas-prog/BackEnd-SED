package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.auditoria.AccesoSistema;

import java.util.List;
import java.util.UUID;

public interface AccesoSistemaRepository extends JpaRepository<AccesoSistema, UUID> {
    List<AccesoSistema> findByUsuarioIdOrderByCreadoEnDesc(UUID usuarioId);
}