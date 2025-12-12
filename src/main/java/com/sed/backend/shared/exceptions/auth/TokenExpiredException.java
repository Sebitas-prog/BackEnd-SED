package com.sed.backend.shared.exceptions.auth;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException(String message) {
        super(message);
    }
}