package cl.lab.laboratorio.crud.model;


import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.entities.Carrera;
import cl.lab.laboratorio.crud.entities.Postulacion;
import cl.lab.laboratorio.crud.entities.Postulante;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto Modelo para la entidad {@link Postulacion}.
 * <p>
 * Este modelo representa una versión general de una postulacion, diseñado específicamente para la transferencia de datos entre las distintas capas de la aplicación
 * (Controladores, Servicios y Repositorios y especialmente en operaciones REST).
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
public class PostulacionModel {

    /**
     * ID único de la postulación.
     * 
     * <p>
     * Corresponde al identificador de la entidad {@link Postulacion}.
     * </p>
     */
    private Long id;

    /**
     * ID del postulante asociado a la postulación.
     * 
     * <p>
     * Referencia al {@link Postulante} mediante su ID.
     * </p>
     */
    private Long postulante_id;

    /**
     * ID de la carrera asociada a la postulación (a la cual postula el postulante).
     * 
     * <p>
     * Referencia a la {@link Carrera} mediante su ID.
     * </p>
     */
    private Long carrera_id;

    /**
     * Año de ingreso del postulante.
     * 
     * <p>
     * Representa el año académico en el cual el postulante aplica la postulación.
     * </p>
     */
    private String ano_ingreso;

    /**
     * Lista de los beneficios solicitados.
     * 
     * <p>
     * Contiene los identificadores de los {@link Beneficio} asociados.
     * </p>
     */
    private List<String> beneficios;

    /**
     * Constructor que crea un Modelo a partir de un objeto {@link Postulacion}.
     * 
     * <p>
     * Se encarga además de controlar los posibles valores vacíos que puede poseer el objeto Postulacion de entrada
     * </p>
     * 
     * @param p Entidad {@link Postulacion} desde la cual se copian los datos
     */
    public PostulacionModel(Postulacion p) {
        this.id = p.getId();
        this.ano_ingreso = p.getAno_ingreso();
        this.carrera_id = (p.getCarrera() != null) ? p.getCarrera().getId() : null; // Manejo de null
        this.postulante_id = (p.getPostulante() != null) ? p.getPostulante().getId() : null; // Manejo de null
        this.beneficios = (p.getBeneficios() != null) ? p.getBeneficios() : new ArrayList<>(); // Manejo de null
    }
}
