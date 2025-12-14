package com.sed.backend.application.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email
    @NotBlank
    private String correo;

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