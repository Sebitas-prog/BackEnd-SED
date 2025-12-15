package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.evaluacion.AsignarInstrumentoRequest;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;
import com.sed.backend.infrastructure.implementations.SeccionServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;
import com.sed.backend.domain.entities.evaluacion.EvaluacionPendiente;
import com.sed.backend.domain.enums.EstadoEvaluacionPendienteEnum;

@Service
@RequiredArgsConstructor
public class AsignarInstrumentoASeccionUseCase {

    private final SeccionRepository seccionRepository;
    private final InstrumentoServiceImpl instrumentoService;
    private final MatriculaRepository matriculaRepository;
    private final SeccionServiceImpl seccionService;

    @Transactional
    public void execute(AsignarInstrumentoRequest request) {
        Seccion seccion = seccionRepository.findById(request.getSeccionId())
                .orElseThrow(() -> new IllegalArgumentException("Seccion no encontrada"));

        Instrumento instrumento = instrumentoService.obtenerPorId(request.getInstrumentoId());

        if (instrumento.getPeriodo() != null &&
                !instrumento.getPeriodo().getId().equals(seccion.getPeriodo().getId())) {
            throw new IllegalArgumentException("La evaluacion pertenece a otro periodo");
        }

        if (seccion.getInstrumento() != null) {
            throw new IllegalArgumentException("La seccion ya tiene una evaluacion asignada");
        }

        seccion.setInstrumento(instrumento);
        Seccion guardada = seccionRepository.save(seccion);

        // Crear pendientes para todos los estudiantes matriculados en la seccion
        seccionService.crearPendientesParaSeccion(guardada, instrumento.getId());
    }
}
