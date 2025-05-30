package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ROL")
@SequenceGenerator(name = "rol_sequence", sequenceName = "rol_sequence", allocationSize = 1, initialValue = 5)
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

}
