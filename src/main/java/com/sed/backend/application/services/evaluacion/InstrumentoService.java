package com.sed.backend.application.services.evaluacion;

import com.sed.backend.domain.entities.evaluacion.Instrumento;

import java.util.List;
import java.util.UUID;

public interface InstrumentoService {
    Instrumento crearInstrumento(Instrumento instrumento);

    List<Instrumento> listar();

    Instrumento obtenerPorId(UUID id);
}