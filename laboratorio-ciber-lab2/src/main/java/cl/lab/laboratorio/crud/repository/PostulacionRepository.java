package cl.lab.laboratorio.crud.repository;

import cl.lab.laboratorio.crud.entities.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link Postulacion}.
 * 
 * <p>
 * Proporciona operaciones CRUD básicas y consultas personalizadas para las Postulaciones mediante Queries de JPA. Además, creados aparte, incluye los métodos
 * para buscar postulaciones por postulante, carrera o facultad.
 * </p>
 * 
 * @author William Zubarzo
 * @version 1.0
 * @since 2025-03-30
 */
@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    /**
     * Busca todas las postulaciones asociadas a un postulante objetivo.
     * 
     * @param id Identificador del postulante que está asociado a la postulación
     * @return Lista de {@link Postulacion} asociadas al postulante buscado
     */
    @Query("SELECT p FROM Postulacion p WHERE p.postulante.id = :id")
    List<Postulacion> findByIdPostulante(Long id);

    /**
     * Busca postulaciones por nombre de carrera
     * 
     * @param nombre_carrera Nombre de la carrera a buscar
     * @return Lista de {@link Postulacion} que coinciden con la carrera ingresada como entrada
     */
    @Query("SELECT p FROM Postulacion p WHERE LOWER(p.carrera.nombre) LIKE LOWER(CONCAT('%', :nombre_carrera, '%'))")
    List<Postulacion> findByCarrera(String nombre_carrera);

    /**
     * Busca postulaciones por nombre de facultad (búsqueda parcial insensible a mayúsculas).
     * 
     * @param nombre_facultad Nombre o parte del nombre de la facultad a buscar
     * @return Lista de {@link Postulacion} que coinciden con el criterio de búsqueda
     */
    @Query("SELECT p FROM Postulacion p WHERE LOWER(p.carrera.facultad) LIKE LOWER(CONCAT('%', :nombre_facultad, '%'))")
    List<Postulacion> findByFacultad(String nombre_facultad);
}
