package com.sed.backend.domain.entities.usuarios;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import com.sed.backend.domain.entities.base.AuditableEntity;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
@AttributeOverride(name = "id", column = @Column(name = "id_usuario", updatable = false, nullable = false))
public class Usuario extends AuditableEntity {

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "correo", nullable = false, unique = true, length = 254)
    private String correo;

    @Column(name = "correo_verificado", nullable = false)
    private boolean correoVerificado = false;

    @Column(name = "contrasena_hash", nullable = false, length = 255)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoUsuarioEnum estado;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "intentos_fallidos", nullable = false)
    private int intentosFallidos = 0;

    @Column(name = "bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UsuarioRol> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TokenVerificacion> tokens = new HashSet<>();

    // Lombok genera automáticamente:
    // - String getPassword()
    // - void setPassword(String password)
    // Con @Setter y @Getter
    @PrePersist
    @PreUpdate
    private void sincronizarNombre() {
        if (nombreCompleto != null) {
            nombreCompleto = nombreCompleto.trim();
        }
    }
}