package com.sed.backend.shared.exceptions.evaluacion;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class EvaluacionAlreadyExistsException extends BusinessException {
    public EvaluacionAlreadyExistsException(String message) {
        super(message);
    }
}