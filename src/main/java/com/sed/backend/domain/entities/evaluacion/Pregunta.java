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
@Table(name = "preguntas")
public class Pregunta extends AuditableEntity {

    @Column(name = "enunciado", nullable = false, length = 500)
    private String enunciado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_id")
    private Criterio criterio;

    // Nota: Lombok genera automáticamente el método id() en el Builder
    // Si aún tienes problemas, asegúrate de tener las anotaciones correctas
}