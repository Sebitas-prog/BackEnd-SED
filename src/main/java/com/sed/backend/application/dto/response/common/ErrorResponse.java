package com.sed.backend.application.dto.response.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String error;
    private final String detail;
}