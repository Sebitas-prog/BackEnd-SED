package com.sed.backend.application.usecases.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sed.backend.application.dto.response.periodo.CursoInscritoResponse;
import com.sed.backend.domain.entities.academico.Matricula;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCursosEstudianteUseCase {

    private final MatriculaRepository matriculaRepository;
    private final PeriodoServiceImpl periodoService;
    private final EvaluacionRepository evaluacionRepository;

    public List<CursoInscritoResponse> execute(UUID estudianteId, UUID periodoId) {
        var periodo = periodoService.buscarPorId(periodoId);
        List<Matricula> matriculas = matriculaRepository
                .findByEstudianteIdAndSeccion_Periodo_Id(estudianteId, periodoId);

        return matriculas.stream()
                .map(m -> map(m, periodo.getNombre()))
                .collect(Collectors.toList());
    }

    private CursoInscritoResponse map(Matricula m, String periodoNombre) {
        var seccion = m.getSeccion();
        var docente = seccion.getDocente();
        boolean completada = evaluacionRepository.existsByMatriculaIdAndSeccionId(m.getId(), seccion.getId());
        UUID instrumentoId = seccion.getInstrumento() != null ? seccion.getInstrumento().getId() : null;
        return CursoInscritoResponse.builder()
                .matriculaId(m.getId())
                .seccionId(seccion.getId())
                .cursoId(seccion.getCurso().getId())
                .cursoCodigo(seccion.getCurso().getCodigo())
                .cursoNombre(seccion.getCurso().getNombre())
                .periodoId(seccion.getPeriodo().getId())
                .periodoNombre(periodoNombre)
                .codigoSeccion(seccion.getCodigo())
                .modalidad(seccion.getModalidad().name())
                .docenteId(docente != null ? docente.getId() : null)
                .docenteNombre(docente != null
                        ? (docente.getNombres() + " " + docente.getApellidos()).trim()
                        : "")
                .instrumentoId(instrumentoId)
                .evaluado(completada)
                .build();
    }
}
