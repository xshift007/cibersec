package cl.lab.laboratorio.crud.services;

import java.util.List;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.PostulanteModel;

/**
 * Interfaz que define las funcionalidades/servicios disponibles para la gestión de un Postulante
 * <p>
 * Proporciona las operaciones CRUD básicas para la entidad Postulante (Create - Crear, Read - Leer, Update - Actualizar, Delete - Eliminar),
 * especificando especialmente el listado de acuerdo a filtros y el listado extra de los beneficios asociados
 * </p>
 * 
 * @author Mauricio Valdés
 * @version 1.2
 * @since 2025-03-29
 */
public interface PostulanteService {
    
    /**
     * Crea un objeto Postulante
     * 
     * @param model Objeto que contiene los datos de la postulación que se creará
     * @throws CrudException si es que no existe el usuario al cual asociar la postulación
     * @throws CrudException si es que no se encuentra la carrera a la cual asociar al usuario
     */
    void crearPostulante(PostulanteModel model);

    /**
     * Modifica un objeto Postulante
     * 
     * @param model Objeto que contiene los datos del postulante que se va a modificar (los nuevos datos)
     * @param id Identificador del postulante que se va a modificar
     * @throws RunTimeException si es que no existe el postulante buscado
     */
    void modificarPostulante(PostulanteModel model, Long id);

    /**
     * Asigna un beneficio específico al postulante objetivo
     * 
     * @param idPostulante Identificador del postulante al cual se le busca asignar un beneficio
     * @param idBeneficio Identificador del beneficio que se le quiere asignar al postulante
     * @throws CrudException si es que el postulante objetivo no existe
     * @throws CrudException si es que el beneficio que se le quiere asignar al postulante no existe
     */
    void asignarBeneficio(Long idPostulante, Long idBeneficio);

    /**
     * Desasigna un beneficio específico al postulante objetivo
     * 
     * @param idPostulante Identificador del postulante al cual se le busca desasignar un beneficio
     * @param idBeneficio Identificador del beneficio que se le quiere desasignar al postulante
     * @throws CrudException si es que el postulante objetivo no existe
     * @throws CrudException si es que el beneficio que se le quiere desasignar al postulante no existe
     */
    void eliminarBeneficio(Long idPostulante, Long idBeneficio);

    /**
     * Busca un postulante en el sistema
     * 
     * @param id Identificador del postulante que se quiere obtener
     * @throws RunTimeException si es que no existe el postulante buscado
     * @return Objeto modelo del postulante encontrado por su identificador
     */
    PostulanteModel obtenerPostulantePorId(Long id);

    /**
     * Lista los beneficios que posee un postulante objetivo
     * 
     * @param idPostulante Identificador del postulante al cual se le quieren ver los beneficios asignados
     * @throws RunTimeException si es que no existe el postulante buscado
     * @return Listado de todos los beneficios que posee el postulante objetivo
     */
    List<Beneficio> listarBeneficiosPorPostulante(Long idPostulante);

    /**
     * Lista al postulante dentro del sistema con un nombre dado
     * 
     * @param nombre String que contiene el nombre del usuario que se quiere filtrar de todos los postulantes del sistema
     * @return Lista con el postulante del nombre ingresado
     */
    List<PostulanteModel> listarPostulantes(String nombre);

    /**
     * Lista al postulante dado un RUT (puede ser rut parcial para buscar coincidencias)
     * 
     * @param rut Identificador parcial de los postulantes a buscar
     * @return Lista con los postulantes que poseen coincidencias con el RUT ingresado
     */
    List<PostulanteModel> listarPostulantesPorRut(String rut);

    /**
     * Elimina un Postulante objetivo
     * 
     * @param id Identificador del postulante a eliminar del sistema
     * @throws CrudException si es que el postulante objetivo no existe
     */
    void eliminarPostulante(Long id);
}
