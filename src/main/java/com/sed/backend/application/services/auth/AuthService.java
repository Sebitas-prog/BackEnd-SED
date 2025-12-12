package com.sed.backend.application.services.auth;

import com.sed.backend.application.dto.request.auth.*;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.application.dto.response.auth.UserInfoResponse;

/**
 * Servicio de autenticación
 * Gestiona registro, login y verificación de usuarios
 */
public interface AuthService {

    /**
     * Registra un nuevo usuario con correo institucional @unas.edu.pe
     * 
     * @param request Datos del registro
     * @return Token de autenticación
     * @throws InvalidEmailDomainException si el correo no es institucional
     * @throws EmailAlreadyExistsException si el correo ya existe
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Autentica un usuario existente
     * 
     * @param request Credenciales de login
     * @return Token de autenticación
     * @throws InvalidCredentialsException si las credenciales son inválidas
     */
    AuthResponse login(LoginRequest request);

    /**
     * Refresca el token de acceso
     * 
     * @param request Token de refresco
     * @return Nuevo token de acceso
     * @throws TokenExpiredException si el token expiró
     */
    AuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Verifica el correo electrónico del usuario
     * 
     * @param request Token de verificación
     * @throws TokenExpiredException si el token expiró
     */
    void verifyEmail(VerifyEmailRequest request);

    /**
     * Cambia la contraseña del usuario
     * 
     * @param request   Datos del cambio de contraseña
     * @param usuarioId ID del usuario autenticado
     */
    void changePassword(ChangePasswordRequest request, String usuarioId);

    /**
     * Solicita recuperación de contraseña
     * 
     * @param email Email del usuario
     */
    void requestPasswordReset(String email);

    /**
     * Obtiene información del usuario autenticado
     * 
     * @param usuarioId ID del usuario
     * @return Información del usuario
     */
    UserInfoResponse getUserInfo(String usuarioId);

    /**
     * Cierra todas las sesiones activas del usuario
     * 
     * @param usuarioId ID del usuario
     */
    void logoutAllSessions(String usuarioId);
}
