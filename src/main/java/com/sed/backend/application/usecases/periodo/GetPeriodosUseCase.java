package com.sed.backend.application.usecases.periodo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sed.backend.application.dto.response.periodo.PeriodoResponse;
import com.sed.backend.application.mappers.PeriodoMapper;
import com.sed.backend.infrastructure.implementations.PeriodoServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPeriodosUseCase {

    private final PeriodoServiceImpl periodoService;

    public List<PeriodoResponse> execute() {
        return periodoService.listarTodos().stream()
                .map(PeriodoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
