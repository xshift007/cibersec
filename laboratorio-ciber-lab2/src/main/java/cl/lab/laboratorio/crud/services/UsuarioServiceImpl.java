package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.dtos.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.lab.laboratorio.crud.entities.Rol;
import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.entities.UsuarioRol;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.RolModel;
import cl.lab.laboratorio.crud.model.UsuarioModel;
import cl.lab.laboratorio.crud.repository.RolRepository;
import cl.lab.laboratorio.crud.repository.UsuarioRepository;
import cl.lab.laboratorio.crud.repository.UsuarioRolRepository;
import cl.lab.laboratorio.crud.utils.Validators;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validators validators;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioRolRepository usuarioRolRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.validators = new Validators();
    }

    @Override
    public void crearUsuario(UsuarioModel model) {
        if (this.usuarioRepository.existsByUsername(model.getUsername())) {
            throw new CrudException("Ya existe un usuario con ese nombre de usuario.");
        }

        if (this.usuarioRepository.existsByCorreo(model.getCorreo())) {
            throw new CrudException("Ya existe un usuario con ese correo.");
        }

        if (!validators.isValidUsername(model.getUsername())) {
            throw new CrudException("El nombre de usuario no es válido.");
        }
        if (!validators.isValidName(model.getNombres())) {
            throw new CrudException("El nombre no es válido.");
        }
        if (!validators.isValidName(model.getApellidos())) {
            throw new CrudException("Los apellidos no son válidos.");
        }
        if (!validators.isValidEmail(model.getCorreo())) {
            throw new CrudException("El correo electrónico no es válido.");
        }
        if (!validators.isValidPassword(model.getPassword())) {
            throw new CrudException("La contraseña no es válida.");
        }

        Rol rol = this.rolRepository.findById(model.getRol().getId())
                .orElseThrow(() -> new CrudException("Rol no encontrado"));

        Usuario usuario = Usuario.builder()
                .username(model.getUsername())
                .nombres(model.getNombres())
                .apellidos(model.getApellidos())
                .correo(model.getCorreo())
                .password(passwordEncoder.encode(model.getPassword()))
                .sid(UUID.randomUUID().toString())
                .accountLocked(false)
                .build();

        UsuarioRol uRol = this.usuarioRolRepository.findByUsuario(model.getUsername());
        if (uRol == null) {
            uRol = new UsuarioRol();
        }

        uRol.setUsuario(usuario);
        uRol.setRol(rol);

        this.usuarioRepository.save(usuario);
        this.usuarioRolRepository.save(uRol);

    }

    @Override
    public void modificarUsuario(UsuarioModel model, Long id) {

        Optional<Usuario> usuarioOpt = this.usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()) {
            throw new CrudException("Usuario no encontrado.");
        }

        Rol rol = this.rolRepository.findById(model.getRol().getId()).get();

        Usuario usuario = usuarioOpt.get();
        usuario.setApellidos(model.getApellidos());
        usuario.setNombres(model.getNombres());
        usuario.setCorreo(model.getCorreo());


        UsuarioRol uRol = this.usuarioRolRepository.findByUsuario(usuario.getUsername());


        uRol.setUsuario(usuario);
        uRol.setRol(rol);

        this.usuarioRolRepository.save(uRol);


        this.usuarioRepository.save(usuario);

    }

    @Override
    public void cambiarContrasena(UsuarioModel model) {
        Optional<Usuario> usuarioOpt = this.usuarioRepository.findById(model.getId());

        if (usuarioOpt.isPresent()) {
            throw new CrudException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(model.getPassword());

        this.usuarioRepository.save(usuario);
    }

    @Override
    public List<UserData> listarUsuarios(String username, String nombre, String apellido) {
        List<UserData> result = this.usuarioRepository.findUsuariosByFilters(nombre, apellido, username);
        //result.forEach(this::completarRoles);

        return result;
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = this.usuarioRepository.findById(id).get();
        UsuarioRol uRol = this.usuarioRolRepository.findByUsuario(usuario.getUsername());
        this.usuarioRolRepository.delete(uRol);
        this.usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioModel obtenerUsuario(String username, String password) {
        UsuarioModel u = this.usuarioRepository.obtenerUsuario(username, password);
        this.completarRoles(u);
        return u;
    }

    private void completarRoles(final UsuarioModel model) {
        UsuarioRol usuarioRol = this.usuarioRolRepository.findByUsuario(model.getUsername());
        Rol rol = usuarioRol.getRol();
        model.setRol(
                RolModel.builder()
                        .id(rol.getId())
                        .descripcion(rol.getDescripcion())
                        .build());
    }
}
