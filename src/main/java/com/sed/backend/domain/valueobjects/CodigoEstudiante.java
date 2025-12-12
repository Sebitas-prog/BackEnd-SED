package com.sed.backend.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object para Código de Estudiante.
 * Formato esperado: números de 8-10 dígitos.
 */
@Getter
@EqualsAndHashCode
@Embeddable
public class CodigoEstudiante {

    private static final Pattern CODIGO_PATTERN = Pattern.compile("^\\d{8,10}$");
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 10;

    @Column(name = "codigo_estudiante")
    private final String valor;

    private CodigoEstudiante(String valor) {
        this.valor = valor;
    }

    /**
     * Crea un Código de Estudiante validando formato y longitud.
     *
     * @param codigo Código a validar
     * @return CodigoEstudiante válido
     * @throws IllegalArgumentException si el código no es válido
     */
    public static CodigoEstudiante of(String codigo) {
        validar(codigo);
        return new CodigoEstudiante(codigo.trim());
    }

    private static void validar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de estudiante no puede estar vacío");
        }

        String codigoLimpio = codigo.trim();

        if (codigoLimpio.length() < MIN_LENGTH || codigoLimpio.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("El código debe tener entre %d y %d dígitos",
                            MIN_LENGTH, MAX_LENGTH));
        }

        if (!CODIGO_PATTERN.matcher(codigoLimpio).matches()) {
            throw new IllegalArgumentException(
                    "El código solo puede contener números");
        }
    }

    /**
     * Obtiene el año de ingreso (primeros 4 dígitos).
     */
    public String getAnioIngreso() {
        return valor.substring(0, 4);
    }

    /**
     * Verifica si el código es válido sin lanzar excepción.
     */
    public static boolean esValido(String codigo) {
        try {
            validar(codigo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}