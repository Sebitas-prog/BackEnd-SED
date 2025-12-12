package com.sed.backend.domain.entities.auditoria;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.entities.usuarios.Usuario;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accesos_sistema")
public class AccesoSistema extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "ip")
    private String ip;

    @Column(name = "navegador")
    private String navegador;

    @Column(name = "fecha_acceso", nullable = false)
    private LocalDateTime fechaAcceso;
}