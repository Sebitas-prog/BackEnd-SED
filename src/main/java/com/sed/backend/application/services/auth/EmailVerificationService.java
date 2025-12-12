package com.sed.backend.application.services.auth;

import com.sed.backend.domain.entities.usuarios.Usuario;

/**
 * Servicio para verificación de correos electrónicos
 */
public interface EmailVerificationService {

    /**
     * Envía email de verificación
     * 
     * @param usuario Usuario a verificar
     */
    void sendVerificationEmail(Usuario usuario);

    /**
     * Reenvía email de verificación
     * 
     * @param email Email del usuario
     */
    void resendVerificationEmail(String email);

    /**
     * Verifica el correo con un token
     * 
     * @param token Token de verificación
     */
    void verifyEmail(String token);
}