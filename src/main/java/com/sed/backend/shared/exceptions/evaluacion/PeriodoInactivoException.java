package com.sed.backend.shared.exceptions.evaluacion;

import com.sed.backend.shared.exceptions.base.BusinessException;

public class PeriodoInactivoException extends BusinessException {
    public PeriodoInactivoException(String message) {
        super(message);
    }
}
