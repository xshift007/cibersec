package cl.lab.laboratorio.crud.entities;

import cl.lab.laboratorio.crud.coverter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ListIterator;

/**
 * Entidad que representa una Postulación en el sistema.
 * 
 * <p>
 * Esta clase modela la postulación de un usuario a una carrera específica, donde también se postula a beneficios disponibles para este usuario.
 * </p>
 *
 * @author William Zubarzo
 * @version 1.0
 * @since 2025-03-30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "postulacion_sequence", sequenceName = "postulacion_sequence", allocationSize = 1, initialValue = 5)
public class Postulacion {

    /**
     * Identificador único de la postulación.
     * 
     * <p>
     * Este identificador se genera automáticamente al momento de crear una postulación, de manera incremental comenzando desde el valor 5 según se indica en {@link SequenceGenerator}
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postulacion_sequence")
    @Column(name = "id")
    private long id;

    /**
     * Año en el cual está ingresando el postulante a la carrera
     */
    @Column(name = "ano_ingreso")
    private String ano_ingreso;

    /**
     * Lista de beneficios a los que se está postulando el postulante.
     * 
     * <p>
     * Se almacena como JSON en la base de datos mediante {@link StringListConverter}
     * </p>
     */
    @Column(name = "beneficios", length = 1000) // Define un tamaño suficiente
    @Convert(converter = StringListConverter.class)
    private List<String> beneficios;

    /**
     * Carrera a la que el postulante desea entrar.
     * 
     * <p>
     * se utiliza {@link ManyToOne} para poder almacenar la llave foránea de las entidad {@link Beneficio} en la presente clase
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    /**
     * Postulante que está haciendo la postulación
     * 
     * <p>
     * se utiliza {@link ManyToOne} para poder almacenar la llave foránea de las entidad {@link Postulante} en la presente clase
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulante_id")
    private Postulante postulante;

}
