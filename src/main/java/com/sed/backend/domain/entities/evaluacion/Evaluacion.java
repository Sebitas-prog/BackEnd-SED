package com.sed.backend.domain.entities.evaluacion;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.entities.base.AuditableEntity;
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

    @Column(name = "canal", nullable = false)
    private String canal;

    @Column(name = "completado_en")
    private LocalDateTime completadoEn;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Respuesta> respuestas = new HashSet<>();

    // Método para obtener el periodo desde la sección
    public Periodo getPeriodo() {
        return seccion != null ? seccion.getPeriodo() : null;
    }

    // Lombok genera automáticamente:
    // - UUID getId()
    // - Seccion getSeccion()
    // - Matricula getMatricula()
    // - EstadoEvaluacionEnum getEstado()
    // - LocalDateTime getCreadoEn() (heredado de AuditableEntity)
}