package com.sed.backend.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.common.ErrorResponse;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        ApiResponse<ErrorResponse> body = ApiResponse.<ErrorResponse>builder()
                .success(false)
                .message("Error de validación")
                .data(ErrorResponse.builder()
                        .error("VALIDATION_ERROR")
                        .detail(detail)
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse<ErrorResponse> body = ApiResponse.<ErrorResponse>builder()
                .success(false)
                .message("Solicitud inválida")
                .data(ErrorResponse.builder()
                        .error("INVALID_REQUEST")
                        .detail(ex.getMessage())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGeneral(Exception ex) {
        ApiResponse<ErrorResponse> body = ApiResponse.<ErrorResponse>builder()
                .success(false)
                .message("Error interno del servidor")
                .data(ErrorResponse.builder()
                        .error("INTERNAL_ERROR")
                        .detail(ex.getMessage())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String formatFieldError(FieldError error) {
        return String.format("%s: %s", error.getField(), error.getDefaultMessage());
    }
}