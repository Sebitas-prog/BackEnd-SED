package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;
import com.sed.backend.domain.valueobjects.RangoFechas;
import com.sed.backend.infrastructure.persistence.repositories.PeriodoRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;
import com.sed.backend.infrastructure.persistence.repositories.InstrumentoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodoServiceImpl {

    private final PeriodoRepository periodoRepository;
    private final SeccionRepository seccionRepository;
    private final InstrumentoRepository instrumentoRepository;

    @Transactional
    public Periodo crearPeriodo(String nombre, RangoFechas rango, EstadoPeriodoEnum estado) {
        Periodo periodo = Periodo.builder()
                .nombre(nombre)
                .fechaInicio(rango.getFechaInicio())
                .fechaFin(rango.getFechaFin())
                .estado(estado)
                .build();
        return periodoRepository.save(periodo);
    }

    @Transactional
    public Periodo actualizarPeriodo(UUID periodoId, String nombre, RangoFechas rango, EstadoPeriodoEnum estado) {
        Periodo periodo = periodoRepository.findById(periodoId)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));
        periodo.setNombre(nombre);
        periodo.setRangoFechas(rango);
        periodo.setEstado(estado);
        return periodoRepository.save(periodo);
    }

    @Transactional(readOnly = true)
    public List<Periodo> listarActivos() {
        return periodoRepository.findByEstado(EstadoPeriodoEnum.ACTIVO);
    }

    @Transactional(readOnly = true)
    public List<Periodo> listarTodos() {
        return periodoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Periodo buscarPorId(UUID id) {
        return periodoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));
    }

    @Transactional
    public Periodo actualizarEstado(UUID periodoId, EstadoPeriodoEnum nuevoEstado) {
        Periodo periodo = periodoRepository.findById(periodoId)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));
        periodo.setEstado(nuevoEstado);
        return periodoRepository.save(periodo);
    }

    @Transactional
    public void eliminar(UUID periodoId) {
        if (!periodoRepository.existsById(periodoId)) {
            throw new IllegalArgumentException("Periodo no encontrado");
        }
        // eliminar secciones y luego instrumentos asociados para no violar FK
        seccionRepository.deleteByPeriodoId(periodoId);
        instrumentoRepository.deleteByPeriodo_Id(periodoId);
        periodoRepository.deleteById(periodoId);
    }
}
