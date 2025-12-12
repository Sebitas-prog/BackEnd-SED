package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Estudiante;

import java.util.Optional;
import java.util.UUID;

public interface EstudianteRepository extends JpaRepository<Estudiante, UUID> {
    Optional<Estudiante> findByEmailIgnoreCase(String email);

    Optional<Estudiante> findByCodigoValor(String codigo);
}