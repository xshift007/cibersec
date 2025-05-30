package cl.lab.laboratorio.crud.auth;

import cl.lab.laboratorio.crud.entities.Rol;
import cl.lab.laboratorio.crud.entities.UsuarioRol;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.repository.UsuarioRepository;
import cl.lab.laboratorio.crud.repository.RolRepository;
import cl.lab.laboratorio.crud.repository.UsuarioRolRepository;
import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.jwt.JwtService;
import cl.lab.laboratorio.crud.config.ApplicationConfig;
import cl.lab.laboratorio.crud.utils.LoginCache;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginCache loginCache;

    public AuthResponse login(LoginRequest request) {
        if (loginCache.isAccountLocked(request.getUsername())) {
            throw new CrudException("La cuenta está bloqueada debido a múltiples intentos fallidos. Intenta más tarde.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            loginCache.loginFailed(request.getUsername());
            throw new CrudException("Username or password is incorrect");
        }

        loginCache.loginSucceeded(request.getUsername());

        Usuario user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .message("Inicio de sesión exitoso")
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombres(request.getNames())
                .apellidos(request.getLastnames())
                .correo(request.getEmail())
                .sid(UUID.randomUUID().toString())
                .accountLocked(false)
                .build();

        userRepository.save(user);

        Rol role = rolRepository.findByDescripcion("POSTULANTE")
                .orElseThrow(() -> new IllegalArgumentException("Role 'POSTULANTE' not found"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(user);
        usuarioRol.setRol(role);

        usuarioRolRepository.save(usuarioRol);

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .message("Registro exitoso")
                .build();
    }


}