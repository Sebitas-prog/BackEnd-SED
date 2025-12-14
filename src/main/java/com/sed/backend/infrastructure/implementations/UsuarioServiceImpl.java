package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.infrastructure.persistence.repositories.UsuarioRepository;
import com.sed.backend.infrastructure.persistence.specifications.UsuarioSpecification;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Page<Usuario> buscarPorFiltro(String termino, Pageable pageable) {
        return usuarioRepository.findAll(UsuarioSpecification.buscarPorTermino(termino), pageable);
    }

    @Transactional
    public Usuario actualizarDatosBasicos(UUID usuarioId, Usuario cambios) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setNombreCompleto(cambios.getNombreCompleto());
        return usuarioRepository.save(usuario);
    }
}