package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.evaluacion.Instrumento;

import java.util.List;
import java.util.UUID;

public interface InstrumentoRepository extends JpaRepository<Instrumento, UUID> {
    List<Instrumento> findByPeriodo_Id(UUID periodoId);

    void deleteByPeriodo_Id(UUID periodoId);
}
