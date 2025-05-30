package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad que representa a un Postulante en el sistema.
 * 
 * <p>
 * Esta clase modela a un postulante dentro del sistema, el cual se encarga de postular a una {@link Carrera} y a ciertos {@link Beneficio}s mediante el proceso de una {@link Postulación}
 * </p>
 *
 * @author Mauricio Valdés
 * @version 1.0
 * @since 2025-03-28
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "postulante_sequence", sequenceName = "postulante_sequence", allocationSize = 1, initialValue = 1)
public class Postulante {
    
    /**
     * Identificador del postulante en el sistema.
     * 
     * <p>
     * Este identificador es único e incremental cada vez que se crea un nuevo postulante, este comienza en el valor 1 como se dicta en {@link SequenceGenerator}
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postulante_sequence")
    @Column(name = "id")
    private Long id;

    /**
     * Usuario asociado al postulante.
     * 
     * <p>
     * Relación One-to-One para establecer relación con un usuario.
     * </p>
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    /**
     * Nombre perteneciente al postulante.
     * 
     * <p>
     * Formato esperado: "Nombre Apellido" o "Nombre ApellidoPaterno ApellidoMaterno".
     * </p>
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * RUT del postulante.
     * 
     * <p>
     * El formato puede ser cualquiera (con/sin puntos y/o guión), pero este será almacenado como un string de solo números. Este valor debe ser único en el sistema.
     * </p>
     */
    @Column(name = "rut")
    private String rut;

    /**
     * Carrera a la que postula el postulante.
     * 
     * <p>
     * Relación Many-to-One para establecer relación entre el postulante y la carrera postulada
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera_id", referencedColumnName = "id", nullable = false)
    private Carrera carrera;

    /**
     * Dirección donde vive el postulante
     */
    @Column(name = "direccion")
    private String direccion;

    /**
     * Notas de Enseñanza Media (NEM) del postulante.
     * </p>
     */
    @Column(name = "nem")
    private BigDecimal nem;

    /**
     * Puntaje de ranking del postulante.
     */
    @Column(name = "ranking")
    private BigDecimal ranking;
}
