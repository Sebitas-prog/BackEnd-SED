package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodoRepository extends JpaRepository<Periodo, UUID> {
    List<Periodo> findByEstado(EstadoPeriodoEnum estado);

    Optional<Periodo> findByNombreIgnoreCase(String nombre);
}