package com.sed.backend.domain.entities.auditoria;

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
@Table(name = "logs_accion")
public class LogAccion extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "detalle", length = 2000)
    private String detalle;

    @Column(name = "exitoso", nullable = false)
    private boolean exitoso;
}