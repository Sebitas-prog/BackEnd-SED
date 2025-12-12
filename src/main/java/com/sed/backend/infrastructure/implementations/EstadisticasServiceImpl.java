package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;
import com.sed.backend.infrastructure.persistence.repositories.RespuestaRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EstadisticasServiceImpl {

    private final EvaluacionRepository evaluacionRepository;
    private final RespuestaRepository respuestaRepository;

    @Transactional(readOnly = true)
    public Map<String, Long> obtenerTotales() {
        long evaluaciones = evaluacionRepository.count();
        long respuestas = respuestaRepository.count();
        return Map.of(
                "totalEvaluaciones", evaluaciones,
                "totalRespuestas", respuestas);
    }
}