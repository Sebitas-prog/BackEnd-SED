package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.configuracion.Parametro;

import java.util.Optional;
import java.util.UUID;

public interface ParametroRepository extends JpaRepository<Parametro, UUID> {
    Optional<Parametro> findByClave(String clave);

    boolean existsByClave(String clave);
}