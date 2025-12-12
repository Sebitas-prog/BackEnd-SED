package com.sed.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret:change-me}")
    private String secret;

    @Value("${security.jwt.expiration:3600}")
    private long expirationSeconds;

    public String getSecret() {
        return secret;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}