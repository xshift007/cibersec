package cl.lab.laboratorio.crud.repository;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.entities.UsuarioRol;
import cl.lab.laboratorio.crud.model.BeneficioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio que implementa funcionalidades SQL de la entidad Beneficio, además de usar las incluídas en el framework de JPA
 * 
 * <p>
 * Provee las funciones CRUD y Queries de JPA, además de utilizar queries propias para listar los beneficios filtrados por nombre, y busqueda de información en la base de datos interna
 * como lo puede ser la búsqueda por código y por nombre
 * </p>
 * 
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    /**
     * Obtiene una lista de beneficios filtrada por el nombre que se ingresa como entrada
     * 
     * <p>
     * Si no se ingresa un nombre, se obtiene una lista con todos los beneficios
     * </p>
     * 
     * @param nombre Nombre por el que se desea filtrar los beneficios
     * @return Lista con los beneficios filtrados por el nombre dado
     */
    @Query("SELECT new cl.lab.laboratorio.crud.model.BeneficioModel(c) " +
            "FROM Beneficio c " +
            "WHERE 1 = 1 " +
            "AND (:nombre IS NULL OR c.nombre LIKE %:nombre%) ")
    List<BeneficioModel> listarBeneficiosPorNombre(@Param("nombre") String nombre);

    /**
     * Verifica que un beneficio exista dado su código interno ingresado
     * 
     * @param codigo Identificador del beneficio a buscar
     * @return Booleano indicando si es que el beneficio con dicho código ya existe en el sistema o no
     */
    boolean existsByCodigo(BigDecimal codigo);

    /**
     * Encuentra un beneficio en el sistema que posea el mismo nombre que se da de entrada
     * 
     * @param nombre Identificador de String que identifica al beneficio a buscar
     * @return {@link Beneficio} con el nombre dado de entrada
     */
    @Query("SELECT b FROM Beneficio b WHERE LOWER(b.nombre) = LOWER(:nombre) ")
    Beneficio findByNombre(String nombre);
}
