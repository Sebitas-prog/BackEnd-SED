package com.sed.backend.application.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos para registrar un usuario institucional.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String nombreCompleto;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
    /**
     * Rol requerido para crear el usuario. Valores permitidos: Estudiante, Docente
     * o Comision.
     */
    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}