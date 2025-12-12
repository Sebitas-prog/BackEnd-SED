package com.sed.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sed.backend.domain.entities.academico.Curso;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {
}