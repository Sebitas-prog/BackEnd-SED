package com.sed.backend.domain.entities.academico;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;
import com.sed.backend.domain.valueobjects.RangoFechas;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "periodos")
public class Periodo extends AuditableEntity {

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPeriodoEnum estado;

    // Método para obtener el rango de fechas como Value Object
    public RangoFechas getRangoFechas() {
        return RangoFechas.of(fechaInicio, fechaFin);
    }

    // Método para establecer el rango de fechas desde un Value Object
    public void setRangoFechas(RangoFechas rangoFechas) {
        if (rangoFechas != null) {
            this.fechaInicio = rangoFechas.getFechaInicio();
            this.fechaFin = rangoFechas.getFechaFin();
        }
    }

    // Lombok genera automáticamente:
    // - UUID getId()
    // - String getNombre()
    // - LocalDate getFechaInicio()
    // - LocalDate getFechaFin()
    // - EstadoPeriodoEnum getEstado()
    // - void setEstado(EstadoPeriodoEnum estado)
}