package com.sed.backend.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.dto.response.common.ErrorResponse;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(Exception ex) {
        String message = "Solicitud inválida";
        ErrorResponse error = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message(message)
                .build();
        ApiResponse<ErrorResponse> body = ApiResponse.<ErrorResponse>builder()
                .success(false)
                .message(message)
                .data(error)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}