package com.sed.backend.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object para Calificación (1-5 estrellas)
 */
@Getter
@EqualsAndHashCode
public class Calificacion {

    public static final int MIN_VALOR = 1;
    public static final int MAX_VALOR = 5;

    private final int valor;

    private Calificacion(int valor) {
        this.valor = valor;
    }

    /**
     * Factory method para crear una Calificación
     * 
     * @param valor Valor entre 1 y 5
     * @return Calificacion válida
     * @throws IllegalArgumentException si el valor no está en el rango
     */
    public static Calificacion of(int valor) {
        validar(valor);
        return new Calificacion(valor);
    }

    private static void validar(int valor) {
        if (valor < MIN_VALOR || valor > MAX_VALOR) {
            throw new IllegalArgumentException(
                    String.format("La calificación debe estar entre %d y %d",
                            MIN_VALOR, MAX_VALOR));
        }
    }

    /**
     * Verifica si es una calificación aprobatoria (≥ 3)
     */
    public boolean esAprobatoria() {
        return valor >= 3;
    }

    /**
     * Verifica si es una calificación excelente (= 5)
     */
    public boolean esExcelente() {
        return valor == MAX_VALOR;
    }

    /**
     * Verifica si es una calificación deficiente (≤ 2)
     */
    public boolean esDeficiente() {
        return valor <= 2;
    }

    /**
     * Obtiene la descripción textual
     */
    public String getDescripcion() {
        return switch (valor) {
            case 1 -> "Muy Deficiente";
            case 2 -> "Deficiente";
            case 3 -> "Regular";
            case 4 -> "Bueno";
            case 5 -> "Excelente";
            default -> "Desconocido";
        };
    }

    @Override
    public String toString() {
        return valor + " - " + getDescripcion();
    }
}