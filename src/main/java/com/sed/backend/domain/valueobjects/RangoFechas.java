package com.sed.backend.domain.valueobjects;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object para Rango de Fechas.
 * Útil para periodos, secciones, etc.
 */
@Getter
@EqualsAndHashCode
public class RangoFechas {

    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;

    private RangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Crea un Rango de Fechas validando que el fin sea posterior al inicio.
     *
     * @param fechaInicio Fecha de inicio
     * @param fechaFin    Fecha de fin
     * @return RangoFechas válido
     * @throws IllegalArgumentException si las fechas son inválidas
     */
    public static RangoFechas of(LocalDate fechaInicio, LocalDate fechaFin) {
        validar(fechaInicio, fechaFin);
        return new RangoFechas(fechaInicio, fechaFin);
    }

    private static void validar(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser nula");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }

    /**
     * Verifica si una fecha está dentro del rango.
     */
    public boolean contiene(LocalDate fecha) {
        return !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
    }

    /**
     * Verifica si el rango está activo (hoy está dentro del rango).
     */
    public boolean estaActivo() {
        return contiene(LocalDate.now());
    }

    /**
     * Obtiene la duración en días.
     */
    public long getDuracionDias() {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
    }

    /**
     * Verifica si se solapa con otro rango.
     */
    public boolean seSolapaCon(RangoFechas otro) {
        return !this.fechaFin.isBefore(otro.fechaInicio) &&
                !otro.fechaFin.isBefore(this.fechaInicio);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", fechaInicio, fechaFin);
    }
}