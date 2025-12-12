package com.sed.backend.application.mappers;

import com.sed.backend.application.dto.response.auth.UserInfoResponse;
import com.sed.backend.domain.entities.usuarios.RolPermiso;
import com.sed.backend.domain.entities.usuarios.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UserInfoResponse toUserInfo(Usuario usuario) {
        Set<String> roles = usuario.getRoles().stream()
                .map(rolUsuario -> rolUsuario.getRol().getNombre())
                .collect(Collectors.toSet());

        // También expone permisos planos si se necesitan
        Set<String> permisos = usuario.getRoles().stream()
                .flatMap(rolUsuario -> rolUsuario.getRol().getPermisos().stream())
                .map(RolPermiso::getPermiso)
                .map(permiso -> permiso.getCodigo())
                .collect(Collectors.toSet());

        roles.addAll(permisos);

        return UserInfoResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .roles(roles)
                .build();
    }
}