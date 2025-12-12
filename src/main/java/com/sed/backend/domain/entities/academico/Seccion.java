package com.sed.backend.domain.entities.academico;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.enums.ModalidadSeccionEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "secciones")
public class Seccion extends AuditableEntity {

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad", nullable = false)
    private ModalidadSeccionEnum modalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_id", nullable = false)
    private Periodo periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id")
    private Docente docente;
}