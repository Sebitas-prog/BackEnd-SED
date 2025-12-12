package com.sed.backend.presentation.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sed.backend.application.dto.request.auth.ChangePasswordRequest;
import com.sed.backend.application.dto.request.auth.LoginRequest;
import com.sed.backend.application.dto.request.auth.RefreshTokenRequest;
import com.sed.backend.application.dto.request.auth.RegisterRequest;
import com.sed.backend.application.dto.request.auth.VerifyEmailRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.application.dto.response.common.ApiResponse;
import com.sed.backend.application.usecases.auth.ChangePasswordUseCase;
import com.sed.backend.application.usecases.auth.LoginUserUseCase;
import com.sed.backend.application.usecases.auth.RefreshTokenUseCase;
import com.sed.backend.application.usecases.auth.RegisterUserUseCase;
import com.sed.backend.application.usecases.auth.VerifyEmailUseCase;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = registerUserUseCase.execute(request);
        ApiResponse<AuthResponse> body = ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Usuario registrado, pendiente de verificación")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = loginUserUseCase.execute(request);
        ApiResponse<AuthResponse> body = ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Autenticación exitosa")
                .data(response)
                .build();
        return ResponseEntity.ok(body);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = refreshTokenUseCase.execute(request);
        ApiResponse<AuthResponse> body = ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Token renovado")
                .data(response)
                .build();
        return ResponseEntity.ok(body);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verify(@Valid @RequestBody VerifyEmailRequest request) {
        verifyEmailUseCase.execute(request);
        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .success(true)
                .message("Correo verificado correctamente")
                .data(null)
                .build();
        return ResponseEntity.ok(body);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        changePasswordUseCase.execute(request);
        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .success(true)
                .message("Contraseña actualizada")
                .data(null)
                .build();
        return ResponseEntity.ok(body);
    }
}