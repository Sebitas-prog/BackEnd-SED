package com.sed.backend.domain.entities.usuarios;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permisos")
public class Permiso extends AuditableEntity {

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "permiso", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RolPermiso> roles = new HashSet<>();
}