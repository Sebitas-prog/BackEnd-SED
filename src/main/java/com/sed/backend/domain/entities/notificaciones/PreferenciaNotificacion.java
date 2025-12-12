package com.sed.backend.domain.entities.notificaciones;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.entities.usuarios.Usuario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "preferencias_notificacion")
public class PreferenciaNotificacion extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_notificacion_id", nullable = false)
    private TipoNotificacion tipoNotificacion;

    @Column(name = "habilitado", nullable = false)
    private boolean habilitado;
}