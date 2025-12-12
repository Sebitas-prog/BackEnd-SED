package com.sed.backend.application.usecases.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.mappers.PeriodoMapper;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPeriodosActivosUseCase {

    private final PeriodoServiceImpl periodoService;

    @Transactional(readOnly = true)
    public List<PeriodoResponse> execute() {
        return periodoService.listarActivos().stream()
                .map(PeriodoMapper::toResponse)
                .collect(Collectors.toList());
    }
}