package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad que representa un Beneficio en el sistema.
 * 
 * <p>
 * Esta clase modela los beneficios que pueden estar asociados tanto a postulantes como a sus postulaciones, incluyendo sus parámetros propios como nombre e identificador
 * </p>
 *
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "beneficio_sequence", sequenceName = "beneficio_sequence", allocationSize = 1, initialValue = 1)
public class Beneficio {

    /**
     * Identificador único del Beneficio
     * 
     * <p>
     * Este identificador se genera automáticamente al momento de crear un beneficio, de manera incremental comenzando desde el valor 1
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beneficio_sequence")
    @Column(name = "id")
    private Long id;

    /**
     * Nombre perteneciente al beneficio
     * 
     * <p>
     * Este nombre debe ser único para poder ser diferenciado de otros beneficios que puedan ser similares en otros parámetros
     * </p>
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Código perteneciente al beneficio
     * 
     * <p>
     * Este código debe ser único para poder ser diferenciado de otros beneficios que puedan ser similares en otros parámetros
     * </p>
     */
    @Column(name ="codigo")
    private BigDecimal codigo;
}
