package com.sed.backend.application.validators;

import org.springframework.stereotype.Component;
import com.sed.backend.domain.valueobjects.Email;

@Component
public class EmailValidator {

    public void validateInstitutional(String email) {
        Email.of(email);
    }
}