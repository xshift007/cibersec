package cl.lab.laboratorio.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import cl.lab.laboratorio.crud.entities.Postulante;
import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.model.PostulanteModel;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad {@link Postulante}.
 * 
 * <p>
 * Proporciona operaciones CRUD básicas mediante el uso del framework JPA y consultas específicas para buscar postulantes por diferentes características tanto exactas como parciales
 * </p>
 * 
 * @author Mauricio Valdés
 * @version 1.1
 * @since 2025-03-29
 */
@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {

    /**
     * Busca un postulante por el ID de un postulante
     * 
     * @param usuarioId ID del {@link Usuario} asociado al postulante
     * @return El {@link Postulante} encontrado
     */
    @Query("SELECT p FROM Postulante p WHERE p.usuario.id = :usuarioId")
    Postulante findByUsuarioId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca un postulante por RUT exacto.
     * 
     * @param rut RUT exacto del postulante que se quiere buscar, permitiendo formatos con punto y guión, solo con uno de ellos, o sin ninguno
     * @return El {@link Postulante} encontrado
     */
    @Query("SELECT p FROM Postulante p WHERE p.rut = :rut")
    Postulante findByRut(@Param("rut") String rut);

    /**
     * Busca postulantes por coincidencia parcial de RUT.
     * 
     * @param rut Texto parcial del RUT a buscar (puede ser {@code null} para listar todos)
     * @return Lista de {@link PostulanteModel} que coinciden con el rut ingresado como entrada
     */
    @Query("SELECT new cl.lab.laboratorio.crud.model.PostulanteModel(p) " +
           "FROM Postulante p " +
           "WHERE (:rut IS NULL OR p.rut LIKE %:rut%)")
    List<PostulanteModel> listarPostulantesPorRut(@Param("rut") String rut);

    /**
     * Busca postulantes por coincidencia parcial en el nombre de cada postulante
     * 
     * @param nombre Texto parcial del nombre a buscar (puede ser {@code null} para listar todos)
     * @return Lista de Postulantes que coinciden con el criterio
     */
    @Query("SELECT new cl.lab.laboratorio.crud.model.PostulanteModel(p) FROM Postulante p WHERE (:nombre IS NULL OR p.nombre LIKE %:nombre%)")
    List<PostulanteModel> listarPostulantes(@Param("nombre") String nombre);

    
}
