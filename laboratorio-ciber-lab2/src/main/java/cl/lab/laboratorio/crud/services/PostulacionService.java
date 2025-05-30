package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.entities.Postulacion;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.PostulacionModel;

import java.util.List;

/**
 * Interfaz que define las funcionalidades/servicios disponibles para la gestión de una Postulación
 * <p>
 * Proporciona las operaciones CRUD básicas para la entidad Postulación (Create - Crear, Read - Leer, Update - Actualizar, Delete - Eliminar), especificando especialmente el listado de acuerdo a filtros
 * </p>
 * 
 * @author William Zubarzo
 * @version 1.3
 * @since 2025-03-30
 */
public interface PostulacionService {

    /**
     * Crea un objeto Postulación
     * 
     * @param model Objeto que contiene los datos de la postulación que se creará
     * @throws RunTimeException si es que no existe el postulante
     */
    void crearPostulacion(final PostulacionModel model);

    /**
     * Modifica un objeto Postulación
     * 
     * @param model Objeto que contiene los datos de la postulación que se va a modificar (los nuevos datos)
     * @param id Identificador de la postulación que se va a modificar
     * @throws RunTimeException si es que no existe la postulación buscada
     */
    void modificarPostulacion(final PostulacionModel model, Long id);

    /**
     * Elimina un objeto Postulación
     * 
     * @param id Identificador de la postulación que se va a eliminar
     */
    void eliminarPostulacion(final Long id);

    /**
     * Lista las postulaciones filtrándolas por postulante
     * 
     * @param id Identificador para poder listar las postulaciones dado el postulante con la id dada
     * @return Listado con las postulaciones filtradas por postulante
     */
    List<PostulacionModel> listarPostulacionesPorPostulanteId(Long id);

    /**
     * Lista las postulaciones filtrándolas por carrera
     * 
     * @param nombre_carrera String para poder listar las postulaciones de acuerdo a la carrera ingresada
     * @return Listado con las postulaciones filtradas por nombre de carrera
     */
    List<PostulacionModel> listarPostulacionesPorCarrera(String nombre_carrera);

    /**
     * Lista las postulaciones filtrándolas por facultad
     * 
     * @param nombre_facultad String para poder listar las postulaciones de acuerdo a la facultad ingresada
     * @return Listado con las postulaciones filtradas por nombre de facultad
     */
    List<PostulacionModel> listarPostulacionesPorFacultad(String nombre_facultad);

    /**
     * Lista todas las postulaciones del sistema
     * 
     * @return Listado con todas las postulaciones del sistema
     */
    List<PostulacionModel> listarPostulaciones();

}
