package com.sed.backend.application.services.usuario;

import com.sed.backend.domain.entities.usuarios.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    Optional<Usuario> buscarPorId(UUID id);

    Usuario actualizarUsuario(Usuario usuario);
}