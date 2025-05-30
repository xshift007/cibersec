package cl.lab.laboratorio.crud.model;

import cl.lab.laboratorio.crud.entities.Usuario;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioModel {

    private Long id;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String correo;
    private RolModel rol;

    public UsuarioModel(Usuario u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.nombres = u.getNombres();
        this.apellidos = u.getApellidos();
        this.correo = u.getCorreo();
    }

}
