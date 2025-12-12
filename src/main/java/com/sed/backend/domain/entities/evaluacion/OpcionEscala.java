package com.sed.backend.domain.entities.evaluacion;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "opciones_escala")
public class OpcionEscala extends AuditableEntity {

    @Column(name = "valor", nullable = false)
    private Integer valor;

    @Column(name = "etiqueta", nullable = false)
    private String etiqueta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escala_id", nullable = false)
    private Escala escala;
}