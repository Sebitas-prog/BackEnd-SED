package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.comision.Comision;

import java.util.UUID;

public interface ComisionRepository extends JpaRepository<Comision, UUID> {
}