package cl.lab.laboratorio.crud.services;

import java.util.List;

import cl.lab.laboratorio.crud.model.CarreraModel;

public interface CarreraService {

    void crearCarreras(CarreraModel model);

    void modificarCarrera(CarreraModel model, Long id);

    List<CarreraModel> listarCarreras(String nombre);

    void eliminarCarrera(Long id);


}
