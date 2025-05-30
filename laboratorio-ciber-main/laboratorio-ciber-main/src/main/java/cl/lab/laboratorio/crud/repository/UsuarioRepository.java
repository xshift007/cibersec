package cl.lab.laboratorio.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.model.UsuarioModel;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT new cl.lab.laboratorio.crud.model.UsuarioModel(u) FROM Usuario u " +
            "WHERE 1 = 1 " +
            "AND (:nombres IS NULL OR u.nombres = :nombres) " +
            "AND (:apellidos IS NULL OR u.apellidos = :apellidos) " +
            "AND (:username IS NULL OR u.username = :username )")
    List<UsuarioModel> findUsuariosByFilters(String nombres, String apellidos, String username);


    @Query("SELECT new cl.lab.laboratorio.crud.model.UsuarioModel(u) " +
            "FROM Usuario u " +
            "WHERE 1 = 1 " +
            "AND u.username = :username " +
            "AND u.password = :password ")
    UsuarioModel obtenerUsuario(String username, String password);


}
