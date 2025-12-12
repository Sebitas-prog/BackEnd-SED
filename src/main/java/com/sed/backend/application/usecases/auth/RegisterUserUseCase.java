package com.sed.backend.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.auth.RegisterRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.application.validators.EmailValidator;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.infrastructure.implementations.AuthServiceImpl;
import com.sed.backend.infrastructure.implementations.EmailVerificationServiceImpl;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final AuthServiceImpl authService;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse execute(RegisterRequest request) {
        emailValidator.validateInstitutional(request.getEmail());

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado(EstadoUsuarioEnum.PENDIENTE_VERIFICACION)
                .build();

        Usuario registrado = authService.registrarUsuario(usuario);
        emailVerificationService.enviarTokenVerificacion(registrado);

        return AuthResponse.builder()
                .token(null)
                .refreshToken(null)
                .tokenType("Bearer")
                .build();
    }
}