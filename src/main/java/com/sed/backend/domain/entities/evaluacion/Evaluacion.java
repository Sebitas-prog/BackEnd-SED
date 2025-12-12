package com.sed.backend.domain.entities.evaluacion;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.enums.CanalEnum;
import com.sed.backend.domain.enums.EstadoEvaluacionEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "evaluaciones")
public class Evaluacion extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id", nullable = false)
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrumento_id", nullable = false)
    private Instrumento instrumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEvaluacionEnum estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal", nullable = false)
    private CanalEnum canal;

    @Column(name = "completado_en")
    private LocalDateTime completadoEn;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Respuesta> respuestas = new HashSet<>();
}