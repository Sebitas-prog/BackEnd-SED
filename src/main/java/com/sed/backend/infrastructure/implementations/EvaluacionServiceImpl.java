package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;
import com.sed.backend.infrastructure.persistence.specifications.EvaluacionSpecification;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EvaluacionServiceImpl {

    private final EvaluacionRepository evaluacionRepository;

    @Transactional
    public Evaluacion registrar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    @Transactional(readOnly = true)
    public Page<Evaluacion> buscarPorPeriodo(UUID periodoId, Pageable pageable) {
        return evaluacionRepository.findAll(
                EvaluacionSpecification.porPeriodo(periodoId), pageable);
    }

    @Transactional(readOnly = true)
    public boolean existeParaMatriculaYSeccion(UUID matriculaId, UUID seccionId) {
        return evaluacionRepository.existsByMatriculaIdAndSeccionId(matriculaId, seccionId);
    }
};
