package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.notificaciones.Notificacion;
import com.sed.backend.infrastructure.adapters.email.EmailAdapter;
import com.sed.backend.infrastructure.persistence.repositories.NotificacionRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl {

    private final NotificacionRepository notificacionRepository;
    private final EmailAdapter emailAdapter;

    @Transactional
    public Notificacion registrar(Notificacion notificacion) {
        Notificacion guardada = notificacionRepository.save(notificacion);
        emailAdapter.enviarEmail(
                guardada.getUsuario().getCorreo(),
                guardada.getTitulo(),
                "notification",
                Map.of(
                        "usuario", guardada.getUsuario().getNombreCompleto(),
                        "mensaje", guardada.getMensaje()));
        return guardada;
    }

    @Transactional(readOnly = true)
    public List<Notificacion> listarPendientes() {
        return notificacionRepository.findAll();
    }
}