package cl.lab.laboratorio.crud.controllers;

import cl.lab.laboratorio.crud.model.BeneficioModel;
import cl.lab.laboratorio.crud.model.ResponseModel;
import cl.lab.laboratorio.crud.services.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de beneficios.
 * 
 * <p>
 * Expone endpoints para operaciones CRUD sobre beneficios, incluyendo su creación, modificación, eliminación y consulta (métodos POST, UPDATE, DELETE, GET).
 * </p>
 *
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;

    /**
     * Obtiene una lista de beneficios, opcionalmente filtrados por nombre.
     * 
     * @param nombre (Opcional) Nombre o parte del nombre del beneficio a buscar
     * @return {@link ResponseModel} conteniendo la lista de {@link BeneficioModel} almacenados en el sistema
     * 
     * @apiNote Ejemplo: GET /beneficios/?nombre=Beca
     */
    @GetMapping("/")
    ResponseModel<List<BeneficioModel>> listarBeneficios(
            @RequestParam(name = "nombre", required = false) String nombre) {
        ResponseModel<List<BeneficioModel>> result = new ResponseModel<>();
        result.setData(this.beneficioService.listarBeneficios(nombre));
        return result;
    }

    /**
     * Crea un nuevo beneficio en el sistema.
     * 
     * @param model Datos del beneficio a crear en formato
     * @return {@link ResponseModel} con mensaje de confirmación de su adición
     * 
     * @apiNote Ejemplo: POST /beneficios/ { "nombre": "Beca Académica", "codigo": 123 }
     */
    @PostMapping("/")
    ResponseModel<Object> crearBeneficio(@RequestBody BeneficioModel model) {
        this.beneficioService.crearBeneficio(model);
        return ResponseModel.builder()
                .message("Beneficio creado correctamente")
                .build();
    }

    /**
     * Modifica un beneficio existente.
     * 
     * @param model Modelo de Beneficio con los nuevos datos a modificar
     * @param id Identificador del beneficio a modificar dentro del sistema
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: PUT /beneficios/1 { "nombre": "Beca Renovada", "codigo": 456 }
     */
    @PutMapping("/{id}")
    ResponseModel<Object> modificarBeneficio(
            @RequestBody BeneficioModel model,
            @PathVariable("id") Long id) {
        this.beneficioService.modificarBeneficio(model, id);
        return ResponseModel.builder()
                .message("Beneficio modificado correctamente")
                .build();
    }

    /**
     * Elimina un beneficio del sistema.
     * 
     * @param id Identificador del beneficio a eliminar
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: DELETE /beneficios/1
     */
    @DeleteMapping("/{id}")
    ResponseModel<Object> eliminarBeneficio(@PathVariable("id") Long id) {
        this.beneficioService.eliminarBeneficio(id);
        return ResponseModel.builder()
                .message("Beneficio eliminado correctamente")
                .build();
    }
}
