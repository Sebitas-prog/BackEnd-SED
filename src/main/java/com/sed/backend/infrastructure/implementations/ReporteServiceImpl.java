package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.infrastructure.persistence.repositories.DocenteRepository;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;
import com.sed.backend.infrastructure.persistence.repositories.PeriodoRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl {

    private final DocenteRepository docenteRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final PeriodoRepository periodoRepository;

    @Transactional(readOnly = true)
    public Map<String, Long> resumenGeneral() {
        return Map.of(
                "docentes", docenteRepository.count(),
                "evaluaciones", evaluacionRepository.count(),
                "periodos", periodoRepository.count());
    }
}