package com.sed.backend.application.mappers;

import com.sed.backend.application.dto.response.evaluacion.InstrumentoResponse;
import com.sed.backend.application.dto.response.evaluacion.ModuloResponse;
import com.sed.backend.application.dto.response.evaluacion.PreguntaResponse;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.domain.entities.evaluacion.Modulo;
import com.sed.backend.domain.entities.evaluacion.Pregunta;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class InstrumentoMapper {

    private InstrumentoMapper() {
    }

    public static InstrumentoResponse toResponse(Instrumento instrumento) {
        return InstrumentoResponse.builder()
                .id(instrumento.getId())
                .nombre(instrumento.getNombre())
                .descripcion(instrumento.getDescripcion())
                .periodoId(instrumento.getPeriodo() != null ? instrumento.getPeriodo().getId() : null)
                .activo(instrumento.isActivo())
                .modulos(toModuloResponses(instrumento.getModulos()))
                .build();
    }

    private static List<ModuloResponse> toModuloResponses(Iterable<Modulo> modulos) {
        return toStream(modulos)
                .sorted(Comparator.comparing(Modulo::getNombre, String.CASE_INSENSITIVE_ORDER))
                .map(modulo -> ModuloResponse.builder()
                        .id(modulo.getId())
                        .nombre(modulo.getNombre())
                        .descripcion(modulo.getDescripcion())
                        .preguntas(toPreguntaResponses(modulo.getPreguntas()))
                        .build())
                .collect(Collectors.toList());
    }

    private static List<PreguntaResponse> toPreguntaResponses(Iterable<Pregunta> preguntas) {
        return toStream(preguntas)
                .sorted(Comparator.comparing(Pregunta::getEnunciado, String.CASE_INSENSITIVE_ORDER))
                .map(pregunta -> PreguntaResponse.builder()
                        .id(pregunta.getId())
                        .enunciado(pregunta.getEnunciado())
                        .build())
                .collect(Collectors.toList());
    }

    private static <T> java.util.stream.Stream<T> toStream(Iterable<T> iterable) {
        if (iterable == null) {
            return java.util.stream.Stream.empty();
        }
        if (iterable instanceof List) {
            return ((List<T>) iterable).stream();
        }
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
