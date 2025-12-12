package com.sed.backend.application.usecases.evaluacion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.response.common.PageResponse;
import com.sed.backend.application.dto.response.evaluacion.EvaluacionResponse;
import com.sed.backend.application.mappers.EvaluacionMapper;
import com.sed.backend.infrastructure.implementations.EvaluacionServiceImpl;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetEvaluacionesUseCase {

    private final EvaluacionServiceImpl evaluacionService;

    @Transactional(readOnly = true)
    public PageResponse<EvaluacionResponse> execute(UUID periodoId, int page, int size) {
        Page<EvaluacionResponse> responsePage = evaluacionService
                .buscarPorPeriodo(periodoId, PageRequest.of(page, size))
                .map(EvaluacionMapper::toResponse);

        return PageResponse.<EvaluacionResponse>builder()
                .content(responsePage.getContent())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .page(page)
                .size(size)
                .build();
    }
}