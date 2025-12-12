package com.sed.backend.application.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private final String token;
    private final String refreshToken;
    private final String tokenType;
}