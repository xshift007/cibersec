package cl.lab.laboratorio.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.lab.laboratorio.crud.entities.Carrera;
import cl.lab.laboratorio.crud.model.CarreraModel;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT new cl.lab.laboratorio.crud.model.CarreraModel(c) " +
            "FROM Carrera c " +
            "WHERE 1 = 1 " +
            "AND (:nombre IS NULL OR c.nombre LIKE %:nombre%) ")
    List<CarreraModel> listarCarreras(@Param("nombre") String nombre);

}
