package com.sed.backend.domain.entities.academico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estados_matricula")
public class EstadoMatricula extends AuditableEntity {

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
