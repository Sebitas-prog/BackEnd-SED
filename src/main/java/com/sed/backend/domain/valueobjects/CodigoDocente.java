package com.sed.backend.domain.valueobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * Value Object para Código de Docente
 * Formato esperado: letras y números, 6-12 caracteres
 */
@Getter
@EqualsAndHashCode
public class CodigoDocente {

    private static final Pattern CODIGO_PATTERN = Pattern.compile("^[A-Z0-9]{6,12}$");
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 12;

    private final String valor;

    private CodigoDocente(String valor) {
        this.valor = valor;
    }

    /**
     * Factory method para crear un Código de Docente
     * 
     * @param codigo Código a validar
     * @return CodigoDocente válido
     * @throws IllegalArgumentException si el código no es válido
     */
    public static CodigoDocente of(String codigo) {
        validar(codigo);
        return new CodigoDocente(codigo.toUpperCase().trim());
    }

    private static void validar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de docente no puede estar vacío");
        }

        String codigoNormalizado = codigo.toUpperCase().trim();

        if (codigoNormalizado.length() < MIN_LENGTH ||
                codigoNormalizado.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("El código debe tener entre %d y %d caracteres",
                            MIN_LENGTH, MAX_LENGTH));
        }

        if (!CODIGO_PATTERN.matcher(codigoNormalizado).matches()) {
            throw new IllegalArgumentException(
                    "El código solo puede contener letras mayúsculas y números");
        }
    }

    /**
     * Verifica si el código es válido sin lanzar excepción
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
