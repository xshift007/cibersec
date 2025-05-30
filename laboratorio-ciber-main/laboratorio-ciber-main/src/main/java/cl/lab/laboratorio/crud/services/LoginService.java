package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.model.UsuarioModel;

public interface LoginService {

    UsuarioModel login(String usuario, String password);

}
