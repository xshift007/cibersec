package cl.lab.laboratorio.crud.model;

import java.math.BigDecimal;

import cl.lab.laboratorio.crud.entities.Beneficio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto Modelo para la entidad {@link Beneficio}.
 * <p>
 * Este modelo representa una versión general de un beneficio, diseñado específicamente para la transferencia de datos entre las distintas capas de la aplicación
 * (Controladores, Servicios y Repositorios y especialmente en operaciones REST).
 * </p>
 *
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficioModel {

    /**
     * Identificador único del beneficio.
     * 
     * <p>
     * Corresponde al ID de la entidad {@link Beneficio} original.
     * </p>
     */
    private Long id;

     /**
     * Nombre del beneficio.
     * 
     * <p>
     * Debe ser único y descriptivo. Como por ejemplo "Beca Académica" o "Ayuda Alimenticia"
     * </p>
     */
    private String nombre;

    /**
     * Código identificador del beneficio.
     * <p>
     * Representación en punto flotante del tipo de dato original ({@link BigDecimal}) de la entidad {@link Beneficio}.
     * </p>
     */
    private Double codigo;

    /**
     * Constructor que crea un Modelo a partir de un objeto {@link Beneficio}.
     * 
     * <p>
     * Realiza la conversión automática de tipos cuando es necesario.
     * </p>
     * 
     * @param p Entidad {@link Beneficio} desde la cual se copian los datos
     */
    public BeneficioModel(Beneficio p) {
        super();
        this.id = p.getId();
        this.codigo = p.getCodigo().doubleValue();
        this.nombre = p.getNombre();
    }
}
