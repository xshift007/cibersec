package cl.lab.laboratorio.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.model.PostulanteModel;
import cl.lab.laboratorio.crud.model.ResponseModel;
import cl.lab.laboratorio.crud.services.PostulanteService;

import java.util.List;

/**
 * Controlador REST para la gestión de postulantes.
 * 
 * <p>
 * Expone endpoints para operaciones CRUD sobre postulantes, incluyendo su creación, modificación, eliminación y consulta (métodos POST, UPDATE, DELETE, GET).
 * </p>
 *
 * @author Mauricio Valdés
 * @version 1.1
 * @since 2025-03-30
 */
@RestController
public class PostulanteController {

    private final PostulanteService postulanteService;

    /**
     * Constructor para inyección de dependencias.
     * 
     * @param postulanteService Servicio de gestión de postulantes
     */
    @Autowired
    public PostulanteController(PostulanteService postulanteService) {
        this.postulanteService = postulanteService;
    }

    /**
     * Obtiene una lista de postulantes, opcionalmente filtrados por nombre.
     * 
     * @param nombre (Opcional) Nombre o parte del nombre del postulante
     * @return {@link ResponseModel} con lista de {@link PostulanteModel}
     * 
     * @apiNote Ejemplo: GET /postulantes/?nombre=Juan
     */
    @GetMapping("/postulantes/")
    ResponseModel<List<PostulanteModel>> listarPostulantes(
            @RequestParam(name = "nombre", required = false) String nombre) {
        ResponseModel<List<PostulanteModel>> result = new ResponseModel<>();
        result.setData(this.postulanteService.listarPostulantes(nombre));
        return result;
    }

    /**
     * Obtiene un postulante por su ID.
     * 
     * @param id ID del postulante a buscar
     * @return {@link ResponseEntity} con el {@link PostulanteModel} encontrado
     * 
     * @apiNote Ejemplo: GET /postulantes/1
     */
    @GetMapping("/postulantes/{id}")
    ResponseEntity<PostulanteModel> obtenerPostulantePorId(@PathVariable("id") Long id) {
        PostulanteModel model = postulanteService.obtenerPostulantePorId(id);
        return ResponseEntity.ok(model);
    }

    /**
     * Obtiene postulantes por RUT.
     * 
     * @param rut (Opcional) RUT o parte del RUT del postulante
     * @return {@link ResponseEntity} con lista de {@link PostulanteModel}
     * 
     * @apiNote Ejemplo: GET /postulantes/rut?rut=12345678
     */
    @GetMapping("/postulantes/rut")
    public ResponseEntity<List<PostulanteModel>> listarPorRut(
        @RequestParam(required = false) String rut) {
        List<PostulanteModel> result = postulanteService.listarPostulantesPorRut(rut);
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene los beneficios asociados a un postulante.
     * 
     * @param postulanteId ID del postulante
     * @return {@link ResponseEntity} con lista de {@link Beneficio}
     * 
     * @apiNote Ejemplo: GET /postulantes/beneficios/1
     */
    @GetMapping("/postulantes/beneficios/{id}")
    ResponseEntity<List<Beneficio>> listarBeneficiosDePostulante(@PathVariable("id") Long postulanteId) {
        List<Beneficio> beneficios = postulanteService.listarBeneficiosPorPostulante(postulanteId);
        return ResponseEntity.ok(beneficios);
    }

    /**
     * Crea un nuevo postulante en el sistema.
     * 
     * @param model Datos del postulante en formato {@link PostulanteModel}
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: POST /postulantes/ { "nombre": "Juan Pérez", "rut": "12345678-9", ... }
     */
    @PostMapping("/postulantes/")
    ResponseModel<Object> crearPostulante(@RequestBody PostulanteModel model) {
        this.postulanteService.crearPostulante(model);
        return ResponseModel.builder()
                .message("Postulante creado correctamente")
                .build();
    }

    /**
     * Actualiza los datos de un postulante existente.
     * 
     * @param model Datos actualizados del postulante
     * @param id ID del postulante a modificar
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: PUT /postulantes/1 { "nombre": "Juan Pérez González", ... }
     */
    @PutMapping("/postulantes/{id}")
    ResponseModel<Object> modificarPostulante(
            @RequestBody PostulanteModel model,
            @PathVariable("id") Long id) {
        this.postulanteService.modificarPostulante(model, id);
        return ResponseModel.builder()
                .message("Postulante modificado correctamente")
                .build();
    }

    /**
     * Asigna un beneficio a un postulante.
     * 
     * @param model Datos del beneficio a asignar
     * @param id ID del postulante
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: PUT /postulantes/1/beneficio { "beneficios": ["Beca Académica"] }
     */
    @PostMapping("/postulantes/{idPostulante}/beneficios/{idBeneficio}")
    ResponseModel<Object> asignarBeneficio(
            @PathVariable("idPostulante") Long idPostulante,
            @PathVariable("idBeneficio") Long idBeneficio) {
        this.postulanteService.asignarBeneficio(idPostulante, idBeneficio);
        return ResponseModel.builder()
                .message("Beneficio asignado correctamente")
                .build();
    }
    /**
     * Desasigna un beneficio a un postulante.
     * 
     * @param model Datos del beneficio a desasignar
     * @param id ID del postulante
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: DELETE /postulantes/1/beneficio { "beneficios": ["Beca Académica"] }
     */
    @DeleteMapping("/postulantes/{idPostulante}/beneficios/{idBeneficio}")
    ResponseModel<Object> eliminarBeneficio(
            @PathVariable("idPostulante") Long idPostulante,
            @PathVariable("idBeneficio") Long idBeneficio) {
        this.postulanteService.eliminarBeneficio(idPostulante, idBeneficio);
        return ResponseModel.builder()
        .message("Beneficio eliminado correctamente")
                .build();
    }

    /**
     * Elimina a un postulante del sistema.
     * 
     * @param id ID del postulante a eliminar
     * @return {@link ResponseModel} con mensaje de confirmación
     * 
     * @apiNote Ejemplo: DELETE /postulantes/1
     */
    @DeleteMapping("/postulantes/{id}")
    ResponseModel<Object> eliminarPostulante(@PathVariable("id") Long id) {
        this.postulanteService.eliminarPostulante(id);
        return ResponseModel.builder()
                .message("Postulante eliminado correctamente")
                .build();
    }
    
}
