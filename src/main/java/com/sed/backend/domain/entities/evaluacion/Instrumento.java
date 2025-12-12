package com.sed.backend.domain.entities.evaluacion;

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
@Table(name = "instrumentos")
public class Instrumento extends AuditableEntity {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @OneToMany(mappedBy = "instrumento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Modulo> modulos = new HashSet<>();

    @OneToMany(mappedBy = "instrumento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<InstrumentoEscala> escalas = new HashSet<>();
}