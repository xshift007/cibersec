package cl.lab.laboratorio.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.lab.laboratorio.crud.entities.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
}
