package cl.lab.laboratorio.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.lab.laboratorio.crud.model.CarreraModel;
import cl.lab.laboratorio.crud.model.ResponseModel;
import cl.lab.laboratorio.crud.services.CarreraService;

import java.util.List;

@RestController
public class CarreraController {

    private final CarreraService carreraService;

    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }


    @GetMapping("/carreras/")
    ResponseModel<List<CarreraModel>> listarCarreras(
            @RequestParam(name = "nombre", required = false) String nombre) {
        ResponseModel<List<CarreraModel>> result = new ResponseModel<>();
        result.setData(this.carreraService.listarCarreras(nombre));
        return result;
    }

    @PostMapping("/carreras/")
    ResponseModel<Object> crearCarrera(@RequestBody CarreraModel model) {
        this.carreraService.crearCarreras(model);
        return ResponseModel.builder()
                .message("Carrera creada correctamente")
                .build();
    }

    @PutMapping("/carreras/{id}")
    ResponseModel<Object> modificarCarrera(
            @RequestBody CarreraModel model,
            @PathVariable("id") Long id) {
        this.carreraService.modificarCarrera(model, id);
        return ResponseModel.builder()
                .message("Carrera modificada correctamente")
                .build();
    }

    @DeleteMapping("/carreras/{id}")
    ResponseModel<Object> eliminarCarrera(@PathVariable("id") Long id) {
        this.carreraService.eliminarCarrera(id);
        return ResponseModel.builder()
                .message("Carrera eliminada correctamente")
                .build();
    }

}
