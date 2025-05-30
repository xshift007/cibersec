package cl.lab.laboratorio.crud.model;

import cl.lab.laboratorio.crud.entities.Usuario;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioModel {

    private Long id;
    private String sid;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String correo;
    private RolModel rol;
    private int failedAttempts;
    private LocalDateTime lastFailedAttempt;
    private boolean accountLocked;

    public UsuarioModel(Usuario u) {
        this.id = u.getId();
        this.sid = u.getSid();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.nombres = u.getNombres();
        this.apellidos = u.getApellidos();
        this.correo = u.getCorreo();
        this.failedAttempts = u.getFailedAttempts();
        this.lastFailedAttempt = u.getLastFailedAttempt();
        this.accountLocked = u.getAccountLocked();
    }

}
