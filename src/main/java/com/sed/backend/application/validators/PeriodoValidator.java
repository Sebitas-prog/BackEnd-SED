package com.sed.backend.application.validators;

import org.springframework.stereotype.Component;
import com.sed.backend.domain.valueobjects.RangoFechas;

import java.time.LocalDate;

@Component
public class PeriodoValidator {

    public RangoFechas buildRange(LocalDate inicio, LocalDate fin) {
        return RangoFechas.of(inicio, fin);
    }
}