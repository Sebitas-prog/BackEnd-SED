package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.usuarios.TokenVerificacion;
import com.sed.backend.domain.enums.TipoTokenEnum;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TokenVerificacionRepository extends JpaRepository<TokenVerificacion, UUID> {
    Optional<TokenVerificacion> findByToken(String token);

    long deleteByUsuarioIdOrExpiraEnBefore(UUID usuarioId, LocalDateTime fechaExpiracion);

    Optional<TokenVerificacion> findFirstByUsuarioIdAndTipoOrderByExpiraEnDesc(UUID usuarioId, TipoTokenEnum tipo);
}