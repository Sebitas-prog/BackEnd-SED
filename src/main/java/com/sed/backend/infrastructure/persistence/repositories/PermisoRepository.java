package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.usuarios.Permiso;

import java.util.Optional;
import java.util.UUID;

public interface PermisoRepository extends JpaRepository<Permiso, UUID> {
    Optional<Permiso> findByCodigoIgnoreCase(String codigo);
}