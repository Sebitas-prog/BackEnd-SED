package com.sed.backend.domain.entities.evaluacion;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.valueobjects.Calificacion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "respuestas")
public class Respuesta extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    @Column(name = "valor_calificacion")
    private Integer valorCalificacion;

    @Column(name = "comentario")
    private String comentario;

    @Transient
    public Calificacion getCalificacion() {
        return valorCalificacion != null ? Calificacion.of(valorCalificacion) : null;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.valorCalificacion = calificacion != null ? calificacion.getValor() : null;
    }

    // Clase Builder personalizada para soportar Calificacion
    public static class RespuestaBuilder {
        public RespuestaBuilder calificacion(Calificacion calificacion) {
            this.valorCalificacion = calificacion != null ? calificacion.getValor() : null;
            return this;
        }
    }
}