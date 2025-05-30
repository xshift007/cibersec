package cl.lab.laboratorio.crud.utils;

import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.entities.UsuarioRol;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Validators {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9][a-zA-Z0-9._]{2,29}[a-zA-Z0-9]$";
        return username.matches(regex);
    }

    public boolean isValidName(String name) {
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";
        return name.matches(regex);
    }

    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return password.matches(regex);
    }

    public boolean isValidRole(String role, String username) {
        Usuario user = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new CrudException("Usuario no encontrado"));

        for (UsuarioRol usuarioRol : user.getUsuarioRoles()) {
            if (usuarioRol.getRol().getDescripcion().equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }
}
