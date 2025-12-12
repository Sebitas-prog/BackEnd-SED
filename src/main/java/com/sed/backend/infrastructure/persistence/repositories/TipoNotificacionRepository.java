package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.notificaciones.TipoNotificacion;

import java.util.Optional;
import java.util.UUID;

public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, UUID> {
    Optional<TipoNotificacion> findByCodigoIgnoreCase(String codigo);
}