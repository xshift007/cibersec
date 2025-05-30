package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1, initialValue = 5)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "password")
    private String password;

    @Column(name = "correo")
    private String correo;

}
