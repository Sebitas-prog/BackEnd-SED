package com.sed.backend.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.auth.ChangePasswordRequest;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.infrastructure.persistence.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(ChangePasswordRequest request) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getContrasenaHash())) {
            throw new IllegalArgumentException("La contraseña actual no es válida");
        }

        usuario.setContrasenaHash(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
    }
}