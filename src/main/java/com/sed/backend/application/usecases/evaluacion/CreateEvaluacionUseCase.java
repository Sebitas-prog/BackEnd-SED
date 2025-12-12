package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.evaluacion.EvaluacionRequest;
import com.sed.backend.application.dto.request.evaluacion.RespuestaRequest;
import com.sed.backend.application.dto.response.evaluacion.EvaluacionResponse;
import com.sed.backend.application.mappers.EvaluacionMapper;
import com.sed.backend.application.validators.EvaluacionValidator;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.domain.entities.evaluacion.Evaluacion;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.domain.entities.evaluacion.Pregunta;
import com.sed.backend.domain.entities.evaluacion.Respuesta;
import com.sed.backend.domain.enums.EstadoEvaluacionEnum;
import com.sed.backend.domain.valueobjects.Calificacion;
import com.sed.backend.infrastructure.implementations.EvaluacionServiceImpl;
import com.sed.backend.infrastructure.implementations.InstrumentoServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.CanalRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateEvaluacionUseCase {

    private final EvaluacionServiceImpl evaluacionService;
    private final InstrumentoServiceImpl instrumentoService;
    private final MatriculaRepository matriculaRepository;
    private final CanalRepository canalRepository;
    private final EvaluacionValidator evaluacionValidator;

    @Transactional
    public EvaluacionResponse execute(EvaluacionRequest request) {
        evaluacionValidator.validate(request);

        Matricula matricula = obtenerMatricula(request.getMatriculaId());
        Instrumento instrumento = instrumentoService.obtenerPorId(request.getInstrumentoId());
        String canal = canalRepository.findByCodigoIgnoreCase("WEB")
                .map(c -> c.getCodigo())
                .orElse("WEB");

        Set<Respuesta> respuestas = construirRespuestas(request.getRespuestas());

        Evaluacion evaluacion = Evaluacion.builder()
                .matricula(matricula)
                .seccion(matricula.getSeccion())
                .instrumento(instrumento)
                .estado(EstadoEvaluacionEnum.EN_PROCESO)
                .canal(canal)
                .completadoEn(LocalDateTime.now())
                .respuestas(respuestas)
                .build();

        respuestas.forEach(r -> r.setEvaluacion(evaluacion));

        Evaluacion guardada = evaluacionService.registrar(evaluacion);
        return EvaluacionMapper.toResponse(guardada);
    }

    private Matricula obtenerMatricula(UUID matriculaId) {
        return matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
    }

    private Set<Respuesta> construirRespuestas(Iterable<RespuestaRequest> requests) {
        Set<Respuesta> respuestas = new HashSet<>();
        for (RespuestaRequest respuestaRequest : requests) {
            Pregunta pregunta = Pregunta.builder()
                    .id(respuestaRequest.getPreguntaId())
                    .build();
            Respuesta respuesta = Respuesta.builder()
                    .pregunta(pregunta)
                    .calificacion(Calificacion.of(respuestaRequest.getValor()))
                    .comentario(respuestaRequest.getComentario())
                    .build();
            respuestas.add(respuesta);
        }
        return respuestas;
    }
}