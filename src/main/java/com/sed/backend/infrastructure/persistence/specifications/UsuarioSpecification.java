package com.sed.backend.infrastructure.persistence.specifications;

import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class UsuarioSpecification {

    private UsuarioSpecification() {
    }

    public static Specification<Usuario> conEmail(String correo) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(correo)) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get("correo")),
                    "%" + correo.toLowerCase() + "%");
        };
    }

    public static Specification<Usuario> conEstado(EstadoUsuarioEnum estado) {
        return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
    }

    public static Specification<Usuario> conNombre(String nombre) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(nombre)) {
                return null;
            }
            String pattern = "%" + nombre.toLowerCase() + "%";
            return cb.like(
                    cb.lower(root.get("nombreCompleto")),
                    pattern);
        };
    }

    // Búsqueda general por término (nombre o correo)
    public static Specification<Usuario> buscarPorTermino(String termino) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(termino)) {
                return null;
            }
            String pattern = "%" + termino.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("nombreCompleto")), pattern),
                    cb.like(cb.lower(root.get("correo")), pattern));
        };
    }
}
