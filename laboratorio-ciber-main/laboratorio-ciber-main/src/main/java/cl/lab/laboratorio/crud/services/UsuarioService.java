package cl.lab.laboratorio.crud.services;

import java.util.List;

import cl.lab.laboratorio.crud.model.UsuarioModel;

public interface UsuarioService {

    void crearUsuario(final UsuarioModel model);

    void modificarUsuario(final UsuarioModel model, Long id);

    void cambiarContrasena(final UsuarioModel model);

    List<UsuarioModel> listarUsuarios(final String username, final String nombre, final String apellido);

    void eliminarUsuario(final Long id);

    UsuarioModel obtenerUsuario(String username, String password);


}
