package com.sed.backend.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.auth.RefreshTokenRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.infrastructure.security.jwt.JwtTokenProvider;
import com.sed.backend.infrastructure.security.userdetails.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Transactional(readOnly = true)
    public AuthResponse execute(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!jwtTokenProvider.validarToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh token inválido");
        }

        String username = jwtTokenProvider.obtenerUsuario(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        String nuevoAccessToken = jwtTokenProvider.generarToken(authentication);
        String nuevoRefresh = jwtTokenProvider.generarRefreshToken(authentication);

        return AuthResponse.builder()
                .token(nuevoAccessToken)
                .refreshToken(nuevoRefresh)
                .tokenType("Bearer")
                .build();
    }
}