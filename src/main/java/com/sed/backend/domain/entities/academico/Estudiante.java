package com.sed.backend.domain.entities.academico;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.valueobjects.CodigoEstudiante;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estudiantes")
public class Estudiante extends AuditableEntity {

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Embedded
    private CodigoEstudiante codigo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}