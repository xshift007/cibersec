package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la relación entre un {@link Postulante} y un {@link Beneficio}.
 * 
 * <p>
 * Esta clase modela a una tabla intermedia entre un {@link Postulante} y un {@link Beneficio} dentro del sistema, para interconectar la relación entre estas dos entidades de mejor manera
 * </p>
 *
 * @author William Zubarzo
 * @version 1.1
 * @since 2025-03-31
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(name = "postulante_beneficio_sequence", sequenceName = "postulante_beneficio_sequence", allocationSize = 1, initialValue = 1)
public class PostulanteBeneficio {
    
    /**
     * Identificador de la relación Postulante-Beneficio
     * 
     * <p>
     * Este identificador es único e incremental cada vez que se crea un nuevo postulante, este comienza en el valor 1 como se dicta en {@link SequenceGenerator}
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postulante_beneficio_sequence")
    @Column(name = "id")
    private Long id;

    /**
     * Postulante asociada a esta relación.
     * 
     * <p>
     * Relación Many-to-One debido a ser la relación intermedia entre dos entidades ({@link Beneficio} y {@link Postulante})
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulante_id")
    private Postulante postulante;

    /**
     * Beneficio asociado a esta relación.
     * 
     * <p>
     * Relación Many-to-One debido a ser la relación intermedia entre dos entidades ({@link Beneficio} y {@link Postulante})
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficio_id")
    private Beneficio beneficio;
}
