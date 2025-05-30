package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.model.BeneficioModel;

import java.util.List;

/**
 * Interfaz que define las funcionalidades/servicios disponibles para la gestión de Beneficios
 * <p>
 * Proporciona las operaciones CRUD básicas para la entidad Beneficio (Create - Crear, Read - Leer, Update - Actualizar, Delete - Eliminar)
 * </p>
 * 
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
public interface BeneficioService {

    /**
     * Crea un objeto Beneficio
     * 
     * @param model Objeto que contiene los datos del beneficio que se creará
     * @throws CrudException si es que ya existe un beneficio igual
     */
    void crearBeneficio(BeneficioModel model);

    /**
     * Modifica un beneficio que ya existe en el sistema
     * 
     * @param model Objeto con los nuevos datos del beneficio
     * @param id Identificador del beneficio a cambiar
     * @throws CrudException si el beneficio no existe
     */
    void modificarBeneficio(BeneficioModel model, Long id);

    /**
     * Obtiene la lista de los beneficios existentes en el sistema por nombre
     * 
     * @param nombre String que sirve para filtrar los beneficios existentes en el sistema
     * @return Lista de los beneficios en el sistema que coinciden con el nombre ingresado como entrada (puede estar vacía si no hay coincidencias)
     */
    List<BeneficioModel> listarBeneficios(String nombre);

    /**
     * Elimina un beneficio del sistema
     * 
     * @param id Identificador del beneficio a eliminar
     */
    void eliminarBeneficio(Long id);
}
