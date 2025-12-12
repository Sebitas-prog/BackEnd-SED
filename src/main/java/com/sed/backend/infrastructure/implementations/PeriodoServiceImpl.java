package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;
import com.sed.backend.domain.valueobjects.RangoFechas;
import com.sed.backend.infrastructure.persistence.repositories.PeriodoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodoServiceImpl {

    private final PeriodoRepository periodoRepository;

    @Transactional
    public Periodo crearPeriodo(String nombre, RangoFechas rango, EstadoPeriodoEnum estado) {
        Periodo periodo = Periodo.builder()
                .nombre(nombre)
                .rangoFechas(rango)
                .estado(estado)
                .build();
        return periodoRepository.save(periodo);
    }

    @Transactional(readOnly = true)
    public List<Periodo> listarActivos() {
        return periodoRepository.findByEstado(EstadoPeriodoEnum.ACTIVO);
    }

    @Transactional
    public Periodo actualizarEstado(UUID periodoId, EstadoPeriodoEnum nuevoEstado) {
        Periodo periodo = periodoRepository.findById(periodoId)
                .orElseThrow(() -> new IllegalArgumentException("Periodo no encontrado"));
        periodo.setEstado(nuevoEstado);
        return periodoRepository.save(periodo);
    }
}