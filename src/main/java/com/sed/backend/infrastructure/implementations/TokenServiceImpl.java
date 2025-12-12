package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.usuarios.TokenVerificacion;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.TipoTokenEnum;
import com.sed.backend.infrastructure.persistence.repositories.TokenVerificacionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl {

    private final TokenVerificacionRepository tokenVerificacionRepository;

    @Transactional
    public TokenVerificacion crearTokenVerificacion(Usuario usuario, TipoTokenEnum tipo, long minutosValidez) {
        String tokenGenerado = UUID.randomUUID().toString();
        TokenVerificacion token = TokenVerificacion.builder()
                .token(tokenGenerado)
                .tipo(tipo)
                .expiraEn(LocalDateTime.now().plusMinutes(minutosValidez))
                .usuario(usuario)
                .build();
        return tokenVerificacionRepository.save(token);
    }

    @Transactional(readOnly = true)
    public Optional<TokenVerificacion> buscarPorToken(String token) {
        return tokenVerificacionRepository.findByToken(token);
    }

    @Transactional
    public void marcarComoUsado(TokenVerificacion token) {
        token.setExpiraEn(LocalDateTime.now());
        tokenVerificacionRepository.save(token);
    }
}