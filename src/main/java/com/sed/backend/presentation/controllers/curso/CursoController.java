package com.sed.backend.presentation.controllers.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.domain.entities.academico.Curso;
import com.sed.backend.infrastructure.persistence.repositories.CursoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Curso>>> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        ApiResponse<List<Curso>> body = ApiResponse.<List<Curso>>builder()
                .success(true)
                .message("Cursos disponibles")
                .data(cursos)
                .build();
        return ResponseEntity.ok(body);
    }
}
