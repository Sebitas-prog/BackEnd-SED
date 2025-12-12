package com.sed.backend.shared.exceptions.common;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}