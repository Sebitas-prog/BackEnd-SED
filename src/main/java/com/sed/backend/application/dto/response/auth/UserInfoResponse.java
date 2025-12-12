package com.sed.backend.application.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class UserInfoResponse {
    private final UUID id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final Set<String> roles;
}