package com.sed.backend.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.auth.LoginRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.infrastructure.implementations.AuthServiceImpl;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final AuthServiceImpl authService;

    @Transactional
    public AuthResponse execute(LoginRequest request) {
        String token = authService.iniciarSesion(
                request.getEmail(), request.getPassword(), request.getIp(), request.getUserAgent());

        return AuthResponse.builder()
                .token(token)
                .refreshToken(null)
                .tokenType("Bearer")
                .build();
    }
}