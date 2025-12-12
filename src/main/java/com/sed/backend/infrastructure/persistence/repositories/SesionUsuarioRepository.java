package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.usuarios.SesionUsuario;

import java.util.UUID;

public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, UUID> {
    long deleteByUsuarioId(UUID usuarioId);
}