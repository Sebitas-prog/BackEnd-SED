package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.notificaciones.PreferenciaNotificacion;

import java.util.List;
import java.util.UUID;

public interface PreferenciaNotificacionRepository extends JpaRepository<PreferenciaNotificacion, UUID> {
    List<PreferenciaNotificacion> findByUsuarioId(UUID usuarioId);
}