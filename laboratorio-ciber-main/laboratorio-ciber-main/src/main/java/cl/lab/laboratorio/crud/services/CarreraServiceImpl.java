package cl.lab.laboratorio.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.lab.laboratorio.crud.entities.Carrera;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.CarreraModel;
import cl.lab.laboratorio.crud.repository.CarreraRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServiceImpl implements CarreraService{

    private final CarreraRepository carreraRepository;

    @Autowired
    public CarreraServiceImpl(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public void crearCarreras(CarreraModel model) {
        Carrera carrera = Carrera.builder()
                .codigo(BigDecimal.valueOf(model.getCodigo()))
                .nombre(model.getNombre())
                .facultad(model.getFacultad())
                .build();

        carreraRepository.save(carrera);
    }

    @Override
    public void modificarCarrera(CarreraModel model, Long id) {

        Optional<Carrera> mascotaOpt = this.carreraRepository.findById(id);
        if (mascotaOpt.isEmpty()) {
            throw new CrudException("carrera no encontrada.");
        }

        Carrera carrera = mascotaOpt.get();
        carrera.setCodigo(BigDecimal.valueOf(model.getCodigo()));
        carrera.setNombre(model.getNombre());
        carrera.setFacultad(model.getFacultad());

        this.carreraRepository.save(carrera);

    }

    @Override
    public List<CarreraModel> listarCarreras(String nombre) {
        return this.carreraRepository.listarCarreras(nombre);
    }

    @Override
    public void eliminarCarrera(Long id) {
        Optional<Carrera> opt = this.carreraRepository.findById(id);
        opt.ifPresent(this.carreraRepository::delete);
    }
}
