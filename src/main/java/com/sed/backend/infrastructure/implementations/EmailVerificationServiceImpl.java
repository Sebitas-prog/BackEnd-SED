package com.sed.backend.infrastructure.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sed.backend.domain.entities.usuarios.TokenVerificacion;
import com.sed.backend.domain.entities.usuarios.Usuario;
import com.sed.backend.domain.enums.EstadoUsuarioEnum;
import com.sed.backend.domain.enums.TipoTokenEnum;
import com.sed.backend.infrastructure.adapters.email.EmailAdapter;
import com.sed.backend.infrastructure.persistence.repositories.TokenVerificacionRepository;
import com.sed.backend.infrastructure.persistence.repositories.UsuarioRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl {

    private final TokenServiceImpl tokenService;
    private final EmailAdapter emailAdapter;
    private final TokenVerificacionRepository tokenVerificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void enviarTokenVerificacion(Usuario usuario) {
        TokenVerificacion token = tokenService.crearTokenVerificacion(
                usuario, TipoTokenEnum.VERIFICACION_EMAIL, 60);

        // COMENTAR TEMPORALMENTE PARA DESARROLLO
        System.out.println("=== TOKEN DE VERIFICACIÓN ===");
        System.out.println("Email: " + usuario.getCorreo());
        System.out.println("Token: " + token.getToken());
        System.out.println("============================");

        // emailAdapter.enviarEmail(
        // usuario.getEmail(),
        // "Verifica tu correo",
        // "verification-email",
        // Map.of("token", token.getToken(), "usuario", usuario.getNombre()));
    }

    @Transactional
    public void verificarCuenta(String tokenPlano) {
        TokenVerificacion token = tokenService.buscarPorToken(tokenPlano)
                .filter(t -> !t.estaVencido())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));

        Usuario usuario = token.getUsuario();
        usuario.setEstado(EstadoUsuarioEnum.ACTIVO);
        usuarioRepository.save(usuario);

        tokenService.marcarComoUsado(token);
        tokenVerificacionRepository.deleteByUsuarioIdOrExpiraEnBefore(usuario.getId(), token.getExpiraEn());
    }
}