package com.sed.backend.application.usecases.seccion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sed.backend.application.dto.response.periodo.SeccionPeriodoResponse;
import com.sed.backend.domain.entities.academico.Seccion;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.EvaluacionRepository;
import com.sed.backend.infrastructure.persistence.repositories.MatriculaRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetSeccionesPorPeriodoUseCase {

    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final PeriodoServiceImpl periodoService;

    public List<SeccionPeriodoResponse> execute(java.util.UUID periodoId) {
        var periodo = periodoService.buscarPorId(periodoId);

        return seccionRepository.findByPeriodoId(periodoId).stream()
                .map(seccion -> mapToResponse(seccion, periodo.getNombre()))
                .collect(Collectors.toList());
    }

    private SeccionPeriodoResponse mapToResponse(Seccion seccion, String periodoNombre) {
        long matriculados = matriculaRepository.countBySeccionId(seccion.getId());
        long evaluaciones = evaluacionRepository.countBySeccionId(seccion.getId());
        String docenteNombre = seccion.getDocente() != null
                ? (seccion.getDocente().getNombres() + " " + seccion.getDocente().getApellidos()).trim()
                : "";

        return SeccionPeriodoResponse.builder()
                .seccionId(seccion.getId())
                .cursoId(seccion.getCurso().getId())
                .cursoNombre(seccion.getCurso().getNombre())
                .periodoId(seccion.getPeriodo().getId())
                .periodoNombre(periodoNombre)
                .codigoSeccion(seccion.getCodigo())
                .modalidad(seccion.getModalidad().name())
                .docenteId(seccion.getDocente() != null ? seccion.getDocente().getId() : null)
                .docenteNombre(docenteNombre)
                .matriculados(matriculados)
                .evaluaciones(evaluaciones)
                .instrumentoId(seccion.getInstrumento() != null ? seccion.getInstrumento().getId() : null)
                .build();
    }
}
