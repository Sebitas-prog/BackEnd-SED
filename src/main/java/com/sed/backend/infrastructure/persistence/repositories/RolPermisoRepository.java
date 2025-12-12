package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.usuarios.RolPermiso;

import java.util.UUID;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, UUID> {
}