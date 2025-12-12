package com.sed.backend.domain.entities.comision;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.entities.usuarios.Usuario;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comisiones")
public class Comision extends AuditableEntity {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @JoinTable(name = "comision_miembros", joinColumns = @JoinColumn(name = "comision_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    @Builder.Default
    private Set<Usuario> miembros = new HashSet<>();
}