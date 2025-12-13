package com.sed.backend.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class CodigoDocente {

    private static final Pattern CODIGO_PATTERN = Pattern.compile("^[A-Z0-9]{6,12}$");
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 12;

    @Column(name = "codigo_docente")
    private String valor;

    private CodigoDocente(String valor) {
        this.valor = valor;
    }

    public static CodigoDocente of(String codigo) {
        validar(codigo);
        CodigoDocente codigoDocente = new CodigoDocente();
        codigoDocente.valor = codigo.toUpperCase().trim();
        return codigoDocente;
    }

    private static void validar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de docente no puede estar vacío");
        }

        String codigoNormalizado = codigo.toUpperCase().trim();

        if (codigoNormalizado.length() < MIN_LENGTH || codigoNormalizado.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("El código debe tener entre %d y %d caracteres",
                            MIN_LENGTH, MAX_LENGTH));
        }

        if (!CODIGO_PATTERN.matcher(codigoNormalizado).matches()) {
            throw new IllegalArgumentException(
                    "El código solo puede contener letras mayúsculas y números");
        }
    }

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