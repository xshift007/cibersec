package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "carrera_sequence", sequenceName = "carrera_sequence", allocationSize = 1, initialValue = 4)
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carrera_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "facultad")
    private String facultad;

    @Column(name ="codigo")
    private BigDecimal codigo;


}
