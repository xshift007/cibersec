package cl.lab.laboratorio.crud.controllers;

import cl.lab.laboratorio.crud.entities.Postulacion;
import cl.lab.laboratorio.crud.model.CarreraModel;
import cl.lab.laboratorio.crud.model.PostulacionModel;
import cl.lab.laboratorio.crud.model.ResponseModel;
import cl.lab.laboratorio.crud.services.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de postulaciones.
 * 
 * <p>
 * Expone endpoints para operaciones CRUD sobre postulaciones, incluyendo su creación, modificación, eliminación y consulta (métodos POST, UPDATE, DELETE, GET).
 * </p>
 *
 * @author William Zubarzo
 * @version 1.1
 * @since 2025-03-30
 */
@RestController
public class PostulacionController {

    private final PostulacionService postulacionService;

    /**
     * Constructor para inyección de dependencias.
     * 
     * @param postulacionService Servicio de gestión de postulaciones
     */
    @Autowired
    public PostulacionController(PostulacionService postulacionService) {
        this.postulacionService = postulacionService;
    }

    /**
     * Obtiene todas las postulaciones existentes en el sistema.
     * 
     * @return {@link ResponseModel} con lista de {@link PostulacionModel}
     * 
     * @apiNote Ejemplo: GET /postulaciones/
     */
    @GetMapping("/postulaciones/")
    public ResponseModel<List<PostulacionModel>> listarPostulaciones() {
        ResponseModel<List<PostulacionModel>> result = new ResponseModel<>();
        result.setData(this.postulacionService.listarPostulaciones());
        return result;
    }

    /**
     * Obtiene las postulaciones por ID de postulante.
     * 
     * @param id Identificador del postulante a buscar
     * @return {@link ResponseModel} con lista de {@link PostulacionModel}
     * 
     * @apiNote Ejemplo: GET /postulaciones/postulante_id?id=1
     */
    @GetMapping("/postulaciones/postulante_id")
    public ResponseModel<List<PostulacionModel>> obtenerPostulacionesPorPostulanteIdPath(@RequestParam Long id) {
        ResponseModel<List<PostulacionModel>> result = new ResponseModel<>();
        result.setData(postulacionService.listarPostulacionesPorPostulanteId(id));
        return result;
    }

    /**
     * Obtiene postulaciones por nombre de facultad.
     * 
     * @param nombre_facultad Nombre de la facultad a filtrar
     * @return {@link ResponseModel} con lista de {@link PostulacionModel}
     * 
     * @apiNote Ejemplo: GET /postulaciones/facultad?nombre_facultad=Ingeniería
     */
    @GetMapping("/postulaciones/facultad")
    public ResponseModel<List<PostulacionModel>> obtenerPostulacionesPorFacultadPath(@RequestParam String nombre_facultad) {
        ResponseModel<List<PostulacionModel>> result = new ResponseModel<>();
        result.setData(postulacionService.listarPostulacionesPorFacultad(nombre_facultad));
        return result;
    }

    /**
     * Obtiene postulaciones por nombre de carrera.
     * 
     * @param nombre_carrera Nombre de la carrera a filtrar
     * @return {@link ResponseModel} con lista de {@link PostulacionModel}
     * 
     * @apiNote Ejemplo: GET /postulaciones/carrera?nombre_carrera=Informática
     */
    @GetMapping("/postulaciones/carrera")
    public ResponseModel<List<PostulacionModel>> obtenerPostulacionesPorCarreraRequest(@RequestParam String nombre_carrera) {
        ResponseModel<List<PostulacionModel>> result = new ResponseModel<>();
        result.setData(postulacionService.listarPostulacionesPorCarrera(nombre_carrera));
        return result;
    }

    /**
     * Crea una nueva postulación en el sistema.
     * 
     * @param postulacionModel Datos de la postulación en formato {@link PostulacionModel}
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: POST /postulaciones/ { "ano_ingreso": "2023", "postulante_id": 1, ... }
     */
    @PostMapping("/postulaciones/")
    public ResponseModel<Object> crearPostulacion(@RequestBody PostulacionModel postulacionModel) {
        this.postulacionService.crearPostulacion(postulacionModel);
        return ResponseModel.builder()
                .message("Postulación creada correctamente")
                .build();
    }

    /**
     * Actualiza una postulación existente en el sistema.
     * 
     * @param model Modelo de una postulaciópn con los datos nuevos a modificar
     * @param id ID de la postulación a modificar
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: PUT /postulaciones/1 { "ano_ingreso": "2024", ... }
     */
    @PutMapping("/postulaciones/{id}")
    ResponseModel<Object> modificarPostulacion(
            @RequestBody PostulacionModel model,
            @PathVariable("id") Long id) {
        this.postulacionService.modificarPostulacion(model, id);
        return ResponseModel.builder()
                .message("Postulacion modificada correctamente")
                .build();
    }

    /**
     * Elimina una postulación del sistema.
     * 
     * @param id ID de la postulación a eliminar
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: DELETE /postulaciones/1
     */
    @DeleteMapping("/postulaciones/{id}")
    ResponseModel<Object> eliminarPostulacion(@PathVariable("id") Long id) {
        this.postulacionService.eliminarPostulacion(id);
        return ResponseModel.builder()
                .message("Postulacion eliminada correctamente")
                .build();
    }

}
