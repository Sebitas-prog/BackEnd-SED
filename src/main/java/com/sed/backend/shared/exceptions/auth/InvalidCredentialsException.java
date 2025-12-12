package com.sed.backend.shared.exceptions.auth;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}