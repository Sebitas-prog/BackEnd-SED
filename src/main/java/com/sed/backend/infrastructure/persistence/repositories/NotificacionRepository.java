package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.notificaciones.Notificacion;

import java.util.List;
import java.util.UUID;

public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByUsuarioIdOrderByEnviadoEnDesc(UUID usuarioId);
}