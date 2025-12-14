package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.usuarios.SesionUsuario;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.infrastructure.persistence.repositories.SesionUsuarioRepository;
import com.sed.backend.infrastructure.persistence.repositories.UsuarioRepository;
import com.sed.backend.infrastructure.security.jwt.JwtTokenProvider;
import com.sed.backend.infrastructure.security.userdetails.CustomUserDetailsService;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio de autenticación básico que interactúa con los repositorios
 * y proveedores de seguridad ya definidos en la infraestructura.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UsuarioRepository usuarioRepository;
    private final SesionUsuarioRepository sesionUsuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEstado(EstadoUsuarioEnum.ACTIVO); // ← AÑADIR ESTA LÍNEA
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica al usuario contra la base y devuelve un token JWT listo para
     * usarse.
     */
    @Transactional
    public String iniciarSesion(String email, String rawPassword, String ip, String userAgent) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        registrarSesion(usuario, ip, userAgent);
        return jwtTokenProvider.generarToken(auth);
    }

    private void registrarSesion(Usuario usuario, String ip, String userAgent) {
        SesionUsuario sesion = SesionUsuario.builder()
                .usuario(usuario)
                .ip(ip)
                .userAgent(userAgent)
                .ultimoAcceso(LocalDateTime.now())
                .build();
        sesionUsuarioRepository.save(sesion);
    }
}