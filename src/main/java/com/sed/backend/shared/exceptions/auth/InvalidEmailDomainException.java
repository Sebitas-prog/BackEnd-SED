package com.sed.backend.shared.exceptions.auth;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class InvalidEmailDomainException extends BusinessException {
    public InvalidEmailDomainException(String message) {
        super(message);
    }
}