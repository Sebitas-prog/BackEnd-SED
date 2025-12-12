package com.sed.backend.application.services.periodo;

import com.sed.backend.domain.entities.academico.Periodo;
import com.sed.backend.domain.enums.EstadoPeriodoEnum;
import com.sed.backend.domain.valueobjects.RangoFechas;

import java.util.List;
import java.util.UUID;

public interface PeriodoService {
    Periodo crearPeriodo(String nombre, RangoFechas rango, EstadoPeriodoEnum estado);

    List<Periodo> listarActivos();

    Periodo actualizarEstado(UUID periodoId, EstadoPeriodoEnum nuevoEstado);
}