package cl.lab.laboratorio.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.lab.laboratorio.crud.entities.UsuarioRol;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    @Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario.username = :username ")
    UsuarioRol findByUsuario(String username);

}
