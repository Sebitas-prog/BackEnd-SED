package com.sed.backend.application.usecases.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.application.dto.request.auth.VerifyEmailRequest;
import com.sed.backend.infrastructure.implementations.EmailVerificationServiceImpl;

@Service
@RequiredArgsConstructor
public class VerifyEmailUseCase {

    private final EmailVerificationServiceImpl emailVerificationService;

    @Transactional
    public void execute(VerifyEmailRequest request) {
        emailVerificationService.verificarCuenta(request.getToken());
    }
}