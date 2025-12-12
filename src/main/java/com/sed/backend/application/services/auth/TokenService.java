package com.sed.backend.application.services.auth;

import com.sed.backend.domain.entities.usuarios.TokenVerificacion;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.TipoTokenEnum;

/**
 * Servicio para gestión de tokens de verificación
 */
public interface TokenService {

    /**
     * Genera un nuevo token de verificación
     * 
     * @param usuario   Usuario para el token
     * @param tipoToken Tipo de token
     * @return Token generado
     */
    TokenVerificacion generateToken(Usuario usuario, TipoTokenEnum tipoToken);

    /**
     * Valida y consume un token
     * 
     * @param token     Token a validar
     * @param tipoToken Tipo esperado de token
     * @return Usuario asociado al token
     * @throws TokenExpiredException si el token expiró
     */
    Usuario validateAndConsumeToken(String token, TipoTokenEnum tipoToken);

    /**
     * Invalida todos los tokens de un usuario
     * 
     * @param usuario   Usuario
     * @param tipoToken Tipo de token a invalidar
     */
    void invalidateUserTokens(Usuario usuario, TipoTokenEnum tipoToken);
}