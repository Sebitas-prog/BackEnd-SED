package com.sed.backend.domain.valueobjects;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object para Email institucional.
 * Encapsula la validación de emails @unas.edu.pe.
 */
@Getter
@EqualsAndHashCode
public class Email {

    private static final String DOMINIO_INSTITUCIONAL = "@unas.edu.pe";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String valor;

    private Email(String valor) {
        this.valor = valor;
    }

    /**
     * Crea un Email institucional validando el dominio.
     *
     * @param email Email a validar
     * @return Email válido
     * @throws IllegalArgumentException si el email no es válido
     */
    public static Email of(String email) {
        validar(email);
        return new Email(email.toLowerCase().trim());
    }

    /**
     * Crea un Email sin validar el dominio institucional.
     * Útil para desarrollo/testing.
     */
    public static Email ofWithoutDomainValidation(String email) {
        if (!esFormatoValido(email)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        return new Email(email.toLowerCase().trim());
    }

    private static void validar(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        String emailNormalizado = email.toLowerCase().trim();

        if (!esFormatoValido(emailNormalizado)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        if (!esInstitucional(emailNormalizado)) {
            throw new IllegalArgumentException(
                    "Solo se permiten correos institucionales " + DOMINIO_INSTITUCIONAL);
        }
    }

    private static boolean esFormatoValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean esInstitucional(String email) {
        return email.endsWith(DOMINIO_INSTITUCIONAL);
    }

    public String getDominio() {
        return valor.substring(valor.indexOf('@'));
    }

    public String getNombreUsuario() {
        return valor.substring(0, valor.indexOf('@'));
    }

    @Override
    public String toString() {
        return valor;
    }
}