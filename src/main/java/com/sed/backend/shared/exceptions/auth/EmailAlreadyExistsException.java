package com.sed.backend.shared.exceptions.auth;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}