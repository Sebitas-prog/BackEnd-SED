package com.sed.backend.shared.exceptions.common;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}