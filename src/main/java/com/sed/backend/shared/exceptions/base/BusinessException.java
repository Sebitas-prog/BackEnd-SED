package com.sed.backend.shared.exceptions.base;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}