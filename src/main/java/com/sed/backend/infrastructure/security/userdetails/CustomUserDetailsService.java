package com.sed.backend.infrastructure.security.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.usuarios.Permiso;
import com.sed.backend.domain.entities.usuarios.Rol;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.infrastructure.persistence.repositories.UsuarioRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (usuario.getEstado() == EstadoUsuarioEnum.PENDIENTE_VERIFICACION) {
            throw new UsernameNotFoundException("El usuario no ha verificado su correo");
        }

        Set<SimpleGrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(usuarioRol -> usuarioRol.getRol())
                .flatMap((Rol rol) -> {
                    Set<SimpleGrantedAuthority> rolAuthorities = rol.getPermisos()
                            .stream()
                            .map(per -> per.getPermiso())
                            .map(Permiso::getCodigo)
                            .map(code -> new SimpleGrantedAuthority("PERM_" + code))
                            .collect(Collectors.toSet());
                    rolAuthorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
                    return rolAuthorities.stream();
                })
                .collect(Collectors.toSet());

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.getEstado() == EstadoUsuarioEnum.ACTIVO,
                true, true, true, authorities);
    }

    public UUID resolveUserId(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(Usuario::getId)
                .orElse(null);
    }
}