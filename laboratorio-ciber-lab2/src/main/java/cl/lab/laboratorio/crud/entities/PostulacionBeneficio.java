package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la relación entre una {@link Postulacion} y un {@link Beneficio}.
 * 
 * <p>
 * Esta clase modela a una tabla intermedia entre una {@link Postulacion} y un {@link Beneficio} dentro del sistema, para interconectar la relación entre estas dos entidades de mejor manera
 * </p>
 *
 * @author Mauricio Valdés
 * @version 1.3
 * @since 2025-03-30
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(name = "postulacion_beneficio_sequence", sequenceName = "postulacion_beneficio_sequence", allocationSize = 1, initialValue = 5)
public class PostulacionBeneficio {

    /**
     * Identificador de la relación Postulación-Beneficio.
     * 
     * <p>
     * Este identificador es único e incremental, este comienza en el valor 5 como se dicta en {@link SequenceGenerator}
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postulacion_beneficio_sequence")
    private Long id;

    /**
     * Beneficio asociado a esta relación.
     * 
     * <p>
     * Relación Many-to-One debido a ser la relación intermedia entre dos entidades ({@link Beneficio} y {@link Postulacion})
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficio_id")
    private Beneficio beneficio;

    /**
     * Postulación asociada a esta relación.
     * 
     * <p>
     * Relación Many-to-One debido a ser la relación intermedia entre dos entidades ({@link Beneficio} y {@link Postulacion})
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulacion_id")
    private Postulacion postulacion;
}
