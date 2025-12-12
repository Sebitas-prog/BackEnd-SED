package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Docente;

import java.util.Optional;
import java.util.UUID;

public interface DocenteRepository extends JpaRepository<Docente, UUID> {
    Optional<Docente> findByCodigoValor(String codigo);
}