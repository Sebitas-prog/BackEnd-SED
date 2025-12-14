package com.sed.backend.domain.entities.usuarios;

import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.enums.TipoTokenEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tokens_verificacion")
public class TokenVerificacion extends AuditableEntity {

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTokenEnum tipo;

    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public boolean estaVencido() {
        return LocalDateTime.now().isAfter(expiraEn);
    }

    // Lombok genera automáticamente:
    // - String getToken()
    // - LocalDateTime getExpiraEn()
    // - void setExpiraEn(LocalDateTime expiraEn)
    // - Usuario getUsuario()
}