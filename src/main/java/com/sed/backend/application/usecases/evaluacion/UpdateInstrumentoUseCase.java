package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.evaluacion.InstrumentoCreateRequest;
import com.sed.backend.application.dto.request.evaluacion.ModuloCreateRequest;
import com.sed.backend.application.dto.request.evaluacion.PreguntaCreateRequest;
import com.sed.backend.application.dto.response.evaluacion.InstrumentoResponse;
import com.sed.backend.application.mappers.InstrumentoMapper;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.domain.entities.evaluacion.Modulo;
import com.sed.backend.domain.entities.evaluacion.Pregunta;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateInstrumentoUseCase {

    private final InstrumentoServiceImpl instrumentoService;
    private final PeriodoServiceImpl periodoService;

    @Transactional
    public InstrumentoResponse execute(UUID instrumentoId, InstrumentoCreateRequest request) {
        Instrumento instrumento = instrumentoService.obtenerPorId(instrumentoId);
        Periodo periodo = periodoService.buscarPorId(request.getPeriodoId());

        instrumento.setNombre(request.getNombre());
        instrumento.setDescripcion(request.getDescripcion());
        instrumento.setPeriodo(periodo);

        instrumento.getModulos().clear();

        Set<Modulo> modulos = new HashSet<>();
        for (ModuloCreateRequest moduloRequest : request.getModulos()) {
            Modulo modulo = Modulo.builder()
                    .nombre(moduloRequest.getNombre())
                    .descripcion(moduloRequest.getDescripcion())
                    .instrumento(instrumento)
                    .build();

            Set<Pregunta> preguntas = new HashSet<>();
            for (PreguntaCreateRequest preguntaRequest : moduloRequest.getPreguntas()) {
                Pregunta pregunta = Pregunta.builder()
                        .enunciado(preguntaRequest.getEnunciado())
                        .modulo(modulo)
                        .build();
                preguntas.add(pregunta);
            }

            modulo.setPreguntas(preguntas);
            modulos.add(modulo);
        }

        instrumento.setModulos(modulos);
        Instrumento actualizado = instrumentoService.actualizar(instrumento);
        return InstrumentoMapper.toResponse(actualizado);
    }
}
