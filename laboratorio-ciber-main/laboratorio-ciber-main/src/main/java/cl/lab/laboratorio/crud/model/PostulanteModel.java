package cl.lab.laboratorio.crud.model;

import cl.lab.laboratorio.crud.entities.Carrera;
import cl.lab.laboratorio.crud.entities.Postulacion;
import cl.lab.laboratorio.crud.entities.Postulante;
import cl.lab.laboratorio.crud.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto Modelo para la entidad {@link Postulante}.
 * <p>
 * Este modelo representa una versión general de un postulante, diseñado específicamente para la transferencia de datos entre las distintas capas de la aplicación
 * (Controladores, Servicios y Repositorios y especialmente en operaciones REST).
 * </p>
 *
 * @author Mauricio Valdés
 * @version 1.0
 * @since 2025-03-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostulanteModel {
    
    /**
     * ID único del postulante.
     * 
     * <p>
     * Corresponde al identificador de la entidad {@link Postulante}.
     * </p>
     */
    private Long id;

    /**
     * ID del usuario asociado al postulante.
     * 
     * <p>
     * Referencia al ID del {@link Usuario} asociado a este postulante.
     * </p>
     */
    private Long usuarioId;

    /**
     * Nombre del postulante.
     */
    private String nombre;

    /**
     * RUT del postulante.
     * 
     * <p>
     * El formato puede ser cualquiera (con/sin puntos y/o guión), pero este será almacenado como un string de solo números. Este valor debe ser único en el sistema.
     * </p>
     */
    private String rut;
    
    /**
     * ID de la carrera asociada al postulante.
     * 
     * <p>
     * Referencia al ID de la {@link Carrera} a la cual el postulante quiere ingresar.
     * </p>
     */
    private Long carrera;

    /**
     * Dirección donde reside el postulante.
     */
    private String direccion;

    /**
     * Notas de Enseñanza Media (NEM) del postulante.
     * 
     * <p>
     * Valor convertido a Double desde BigDecimal (BigDecimal se encuentra en la entidad de Postulante)
     * </p>
     */
    private Double nem;

    /**
     * Puntaje de ranking del postulante.
     * 
     * <p>
     * Utiliza tipo de dato Double a diferencia de BigDecimal como en la entidad correspondiente
     * </p>
     */
    private Double ranking;

    /**
     * Constructor que crea un Modelo a partir de un objeto {@link Postulante}.
     * 
     * <p>
     * Se encarga además de controlar los posibles valores vacíos que puede poseer el objeto Postulante de entrada
     * </p>
     * 
     * @param p Entidad {@link Postulante} desde la cual se copian los datos
     */
    public PostulanteModel(Postulante p) {
        super();
        this.id = p.getId();
        this.usuarioId = p.getUsuario().getId();
        this.nombre = p.getNombre();
        this.rut = p.getRut();
        this.carrera = p.getCarrera().getId();
        this.direccion = p.getDireccion();
        this.nem = p.getNem() != null ? p.getNem().doubleValue() : null;
        this.ranking = p.getRanking() != null ? p.getRanking().doubleValue() : null;
    }
}
