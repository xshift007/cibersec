package cl.lab.laboratorio.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.entities.Postulante;
import cl.lab.laboratorio.crud.entities.PostulanteBeneficio;
import jakarta.transaction.Transactional;

/**
 * Repositorio para la gestión de la relación entre {@link Postulante} y {@link Beneficio} que utiliza las funciones básicas de CRUD y el framework de JPA, además de añador
 * queries para la busqueda de beneficios por postulante específico y buscar postulante por su id específico.
 * 
 * @author Mauricio Valdés
 * @version 1.1
 * @since 2025-03-30
 */
@Repository
public interface PostulanteBeneficioRepository extends JpaRepository<PostulanteBeneficio, Long> {

    /**
     * Busca la relación Postulante-Beneficio por ID de postulante.
     * 
     * @param postulanteId Identificador del {@link Postulante} a buscar
     * @return Objeto {@link PostulanteBeneficio} que establece la relación encontrada entre estas dos entidades
     */
    @Query("SELECT pb FROM PostulanteBeneficio pb WHERE pb.postulante.id = :postulanteId")
    PostulanteBeneficio findByPostulanteId(Long postulanteId);

    /**
     * Obtiene todos los beneficios asociados a un postulante específico.
     * 
     * @param postulanteId Identificador del {@link Postulante} que se quiere consultar
     * @return Lista de Beneficios asociados al postulante objetivo.
     */
    @Query("SELECT b FROM Beneficio b " +
           "JOIN PostulanteBeneficio pb ON b.id = pb.beneficio.id " +
           "WHERE pb.postulante.id = :postulanteId")
    List<Beneficio> findBeneficiosByPostulanteId(@Param("postulanteId") Long postulanteId);

    /**
     * Elimina todas las relaciones de un postulante específico.
     * 
     * <p>
     * Operación transaccional que requiere escritura en base de datos.
     * </p>
     * 
     * @param postulanteId ID del {@link Postulante} cuyas relaciones se eliminarán
     * 
     * @apiNote Ejemplo: DELETE FROM postulante_beneficio WHERE postulante_id = [postulanteId]
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostulanteBeneficio pb WHERE pb.postulante.id = :postulanteId")
    void deleteByPostulanteId(@Param("postulanteId") Long postulanteId);

    /**
     * Elimina todas las relaciones de un beneficio específico.
     * <p>
     * Operación transaccional que requiere escritura en base de datos.
     * </p>
     * 
     * @param beneficioId ID del {@link Beneficio} cuyas relaciones se eliminarán
     * 
     * @apiNote Ejemplo: DELETE FROM postulante_beneficio WHERE beneficio_id = [beneficioId]
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostulanteBeneficio pb WHERE pb.beneficio.id = :beneficioId")
    void deleteByBeneficioId(@Param("beneficioId") Long beneficioId);

    /**
     * Elimina una relación específica entre postulante y beneficio.
     * <p>
     * Operación transaccional que requiere escritura en base de datos.
     * </p>
     * 
     * @param idPostulante ID del {@link Postulante} en la relación
     * @param idBeneficio ID del {@link Beneficio} en la relación
     * 
     * @apiNote Ejemplo: DELETE FROM postulante_beneficio 
     *          WHERE postulante_id = [idPostulante] AND beneficio_id = [idBeneficio]
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM PostulanteBeneficio pb WHERE pb.postulante.id = :idPostulante AND pb.beneficio.id = :idBeneficio")
    void deleteByPostulanteIdAndBeneficioId(Long idPostulante, Long idBeneficio);
    
}
