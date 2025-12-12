package com.sed.backend.shared.exceptions.common;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message) {
        super(message);
    }
}