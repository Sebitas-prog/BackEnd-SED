package com.sed.backend.application.usecases.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sed.backend.application.dto.response.periodo.CursoInscritoResponse;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCursosDocenteUseCase {

    private final SeccionRepository seccionRepository;
    private final PeriodoServiceImpl periodoService;

    public List<CursoInscritoResponse> execute(UUID docenteId, UUID periodoId) {
        var periodo = periodoService.buscarPorId(periodoId);
        List<Seccion> secciones = seccionRepository.findByDocenteIdAndPeriodoId(docenteId, periodoId);
        return secciones.stream()
                .map(s -> map(s, periodo.getNombre()))
                .collect(Collectors.toList());
    }

    private CursoInscritoResponse map(Seccion s, String periodoNombre) {
        var docente = s.getDocente();
        return CursoInscritoResponse.builder()
                .seccionId(s.getId())
                .cursoId(s.getCurso().getId())
                .cursoCodigo(s.getCurso().getCodigo())
                .cursoNombre(s.getCurso().getNombre())
                .periodoId(s.getPeriodo().getId())
                .periodoNombre(periodoNombre)
                .codigoSeccion(s.getCodigo())
                .modalidad(s.getModalidad().name())
                .docenteId(docente != null ? docente.getId() : null)
                .docenteNombre(docente != null
                        ? (docente.getNombres() + " " + docente.getApellidos()).trim()
                        : "")
                .build();
    }
}
