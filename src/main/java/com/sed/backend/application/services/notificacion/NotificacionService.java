package com.sed.backend.application.services.notificacion;

import com.sed.backend.domain.entities.notificaciones.Notificacion;

import java.util.List;
import java.util.UUID;

public interface NotificacionService {
    Notificacion enviar(Notificacion notificacion);

    List<Notificacion> listarPorUsuario(UUID usuarioId);
}