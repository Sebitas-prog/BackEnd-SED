package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.evaluacion.Instrumento;
import com.sed.backend.infrastructure.persistence.repositories.InstrumentoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstrumentoServiceImpl {

    private final InstrumentoRepository instrumentoRepository;

    @Transactional
    public Instrumento guardar(Instrumento instrumento) {
        return instrumentoRepository.save(instrumento);
    }

    @Transactional(readOnly = true)
    public List<Instrumento> listarTodos() {
        return instrumentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Instrumento obtenerPorId(UUID id) {
        return instrumentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrumento no encontrado"));
    }
}