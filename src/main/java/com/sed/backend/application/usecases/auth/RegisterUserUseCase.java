package com.sed.backend.application.usecases.auth;

import com.sed.backend.application.dto.request.auth.RegisterRequest;
import com.sed.backend.application.dto.response.auth.AuthResponse;
import com.sed.backend.application.validators.EmailValidator;
import com.sed.backend.domain.entities.academico.Docente;
import com.sed.backend.domain.entities.academico.Estudiante;
import com.sed.backend.domain.entities.usuarios.Rol;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.entities.usuarios.UsuarioRol;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.infrastructure.implementations.AuthServiceImpl;
import com.sed.backend.infrastructure.implementations.EmailVerificationServiceImpl;
import com.sed.backend.infrastructure.persistence.repositories.DocenteRepository;
import com.sed.backend.infrastructure.persistence.repositories.EstudianteRepository;
import com.sed.backend.infrastructure.persistence.repositories.RolRepository;
import com.sed.backend.domain.valueobjects.CodigoEstudiante;
import com.sed.backend.domain.valueobjects.CodigoDocente;
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
    private final EstudianteRepository estudianteRepository;
    private final DocenteRepository docenteRepository;

    @Transactional
    public AuthResponse execute(RegisterRequest request) {
        emailValidator.validateInstitutional(request.getCorreo());

        Rol rol = rolRepository.findByNombreIgnoreCase(request.getRol())
                .filter(r -> esRolPermitido(r.getNombre()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rol invalido. Usa Estudiante, Docente o Comision"));

        Usuario usuario = Usuario.builder()
                .nombreCompleto((request.getNombres() + " " + request.getApellidos()).trim())
                .correo(request.getCorreo())
                .contrasenaHash(request.getPassword())
                .estado(EstadoUsuarioEnum.ACTIVO)
                .build();

        Usuario registrado = authService.registrarUsuario(usuario);

        // Crear perfil segun rol
        java.util.UUID perfilId = null;
        String rolNombre = rol.getNombre().trim().toUpperCase();
        switch (rolNombre) {
            case "ESTUDIANTE" -> {
                if (request.getCodigo() == null || request.getCodigo().isBlank()) {
                    throw new IllegalArgumentException("El codigo de estudiante es obligatorio");
                }
                Estudiante est = Estudiante.builder()
                        .usuario(registrado)
                        .nombres(request.getNombres())
                        .apellidos(request.getApellidos())
                        .email(request.getCorreo())
                        .codigo(CodigoEstudiante.of(request.getCodigo()))
                        .build();
                perfilId = estudianteRepository.save(est).getId();
            }
            case "DOCENTE" -> {
                Docente doc = Docente.builder()
                        .usuario(registrado)
                        .nombres(request.getNombres())
                        .apellidos(request.getApellidos())
                        .email(request.getCorreo())
                        .codigo(request.getCodigo() != null && !request.getCodigo().isBlank()
                                ? CodigoDocente.of(request.getCodigo())
                                : null)
                        .build();
                perfilId = docenteRepository.save(doc).getId();
            }
            default -> {
                // COMISION u otros no crean perfil academico
            }
        }

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .usuario(registrado)
                .rol(rol)
                .build();

        registrado.getRoles().add(usuarioRol);

        // emailVerificationService.enviarTokenVerificacion(registrado); // opcional

        return AuthResponse.builder()
                .token(null)
                .refreshToken(null)
                .tokenType("Bearer")
                .usuarioId(registrado.getId())
                .perfilId(perfilId)
                .rol(rol.getNombre())
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
