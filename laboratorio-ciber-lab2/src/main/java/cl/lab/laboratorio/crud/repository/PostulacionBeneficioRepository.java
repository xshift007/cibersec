package cl.lab.laboratorio.crud.repository;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.entities.Postulacion;
import cl.lab.laboratorio.crud.entities.PostulacionBeneficio;
import cl.lab.laboratorio.crud.entities.UsuarioRol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la relación entre {@link Postulacion} y {@link Beneficio} que implementa el CRUD básico y queries de JPA adempas de funciones específicas como la búsqueda de beneficios
 * filtrados por ciertos parámetros (Nombre, Postulación, Id)
 * 
 * <p>
 * Proporciona operaciones para consultar y eliminar las relaciones entre postulaciones y beneficios, permitiendo gestionar los beneficios asociados a cada postulación.
 * </p>
 * 
 * @author William Zubarzo
 * @version 1.0
 * @since 2025-03-30
 */
@Repository
public interface PostulacionBeneficioRepository extends JpaRepository<PostulacionBeneficio, Long> {

    /**
     * Busca una relación Postulación-Beneficio dado un nombre de entrada.
     * 
     * @param nombre Nombre del beneficio
     * @return La relación {@link PostulacionBeneficio} encontrada
     */
    @Query("SELECT pb FROM PostulacionBeneficio pb WHERE pb.beneficio.nombre = :nombre ")
    PostulacionBeneficio findByNombreBeneficio(String nombre);

     /**
     * Busca una relación Postulación-Beneficio por postulación específica.
     * 
     * @param id Identificador de la {@link Postulacion} para buscar su relación con {@link Beneficio}
     * @return La relación {@link PostulacionBeneficio} encontrada
     */
    @Query("SELECT pb FROM PostulacionBeneficio pb WHERE pb.postulacion.id = :id ")
    PostulacionBeneficio findByIdPostulacion(Long id);

    /**
     * Busca una relación Postulación-Beneficio por el Identificador de un beneficio.
     * 
     * @param id Identificador del {@link Beneficio} para buscar su relación con una/s {@link Postulacion}
     * @return La relación {@link PostulacionBeneficio} encontrada
     */
    @Query("SELECT pb FROM PostulacionBeneficio pb WHERE pb.beneficio.id = :id ")
    PostulacionBeneficio findByIdBeneficio(Long id);

    /**
     * Elimina todas las relaciones Postulación-Beneficio asociadas a una postulación objetivo.
     * 
     * <p>
     * Se utiliza {@link Transactional} para indicar que es una operación importante que no puede ser interrumpida, ya que reemplaza valores dentro de la base de datos
     * </p>
     * 
     * @param postulacionId Identificador de la {@link Postulacion} de la cual se eliminarán sus relaciones con ciertos beneficios
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostulacionBeneficio pb WHERE pb.postulacion.id = :postulacionId")
    void deleteByPostulacionId(Long postulacionId);

    /**
     * Elimina todas las relaciones Postulación-Beneficio asociadas a un beneficio objetivo.
     * 
     * <p>
     * Se utiliza {@link Transactional} para indicar que es una operación importante que no puede ser interrumpida, ya que reemplaza valores dentro de la base de datos
     * </p>
     * 
     * @param beneficioId Identificador del {@link Beneficio} del cual se eliminarán sus relaciones con ciertas postulaciones
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostulacionBeneficio pb WHERE pb.beneficio.id = :beneficioId")
    void deleteByBeneficioId(Long beneficioId);

}
