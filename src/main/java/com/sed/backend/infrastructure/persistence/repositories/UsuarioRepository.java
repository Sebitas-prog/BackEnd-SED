package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sed.backend.domain.entities.usuarios.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCase(String correo);
}