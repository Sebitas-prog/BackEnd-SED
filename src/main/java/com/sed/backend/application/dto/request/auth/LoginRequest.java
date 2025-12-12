package com.sed.backend.application.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    /**
     * IP del cliente para auditoría.
     */
    private String ip;

    /**
     * User-Agent del cliente para auditoría.
     */
    private String userAgent;
}