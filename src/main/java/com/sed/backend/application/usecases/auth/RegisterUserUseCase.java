package com.sed.backend.application.usecases.auth;

import com.sed.backend.application.dto.request.auth.RegisterRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.application.validators.EmailValidator;
import com.sed.backend.domain.entities.usuarios.Rol;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.entities.usuarios.UsuarioRol;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.infrastructure.implementations.AuthServiceImpl;
import com.sed.backend.infrastructure.implementations.EmailVerificationServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final AuthServiceImpl authService;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final EmailValidator emailValidator;
    private final RolRepository rolRepository;

    @Transactional
    public AuthResponse execute(RegisterRequest request) {
        emailValidator.validateInstitutional(request.getCorreo());

        Rol rol = rolRepository.findByNombreIgnoreCase(request.getRol())
                .filter(r -> esRolPermitido(r.getNombre()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rol inválido. Usa Estudiante, Docente o Comision"));

        Usuario usuario = Usuario.builder()
                .nombreCompleto(request.getNombreCompleto())
                .correo(request.getCorreo())
                .contrasenaHash(request.getPassword())
                .estado(EstadoUsuarioEnum.ACTIVO) // ← CAMBIAR AQUÍ si corresponde
                .build();

        Usuario registrado = authService.registrarUsuario(usuario);

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .usuario(registrado) // mejor usar el ya persistido
                .rol(rol)
                .build();

        registrado.getRoles().add(usuarioRol);

        // COMENTAR TEMPORALMENTE para desarrollo
        // emailVerificationService.enviarTokenVerificacion(registrado);

        return AuthResponse.builder()
                .token(null)
                .refreshToken(null)
                .tokenType("Bearer")
                .build();
    }

    private boolean esRolPermitido(String nombreRol) {
        if (nombreRol == null)
            return false;

        return switch (nombreRol.trim().toUpperCase()) {
            case "ESTUDIANTE", "DOCENTE", "COMISION" -> true;
            default -> false;
        };
    }
}
