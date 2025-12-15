package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.infrastructure.persistence.repositories.InstrumentoRepository;
import com.sed.backend.infrastructure.persistence.repositories.SeccionRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstrumentoServiceImpl {

    private final InstrumentoRepository instrumentoRepository;
    private final SeccionRepository seccionRepository;

    @Transactional
    public Instrumento guardar(Instrumento instrumento) {
        return instrumentoRepository.save(instrumento);
    }

    @Transactional
    public Instrumento actualizar(Instrumento instrumento) {
        return instrumentoRepository.save(instrumento);
    }

    @Transactional(readOnly = true)
    public List<Instrumento> listarTodos() {
        return instrumentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Instrumento> listarPorPeriodo(UUID periodoId) {
        return instrumentoRepository.findByPeriodo_Id(periodoId);
    }

    @Transactional(readOnly = true)
    public Instrumento obtenerPorId(UUID id) {
        return instrumentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrumento no encontrado"));
    }

    @Transactional
    public void eliminar(UUID instrumentoId) {
        Instrumento inst = instrumentoRepository.findById(instrumentoId)
                .orElseThrow(() -> new IllegalArgumentException("Instrumento no encontrado"));

        // Eliminar asociaciones de secciones y luego el instrumento
        seccionRepository.deleteByInstrumento_Id(instrumentoId);
        instrumentoRepository.delete(inst);
    }
}
