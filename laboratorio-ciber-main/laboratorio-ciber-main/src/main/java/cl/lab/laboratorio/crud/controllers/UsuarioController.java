package cl.lab.laboratorio.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.lab.laboratorio.crud.model.ResponseModel;
import cl.lab.laboratorio.crud.model.UsuarioModel;
import cl.lab.laboratorio.crud.services.UsuarioService;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users/")
    ResponseModel<List<UsuarioModel>> listarUsuarios(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido) {

        ResponseModel<List<UsuarioModel>> result = new ResponseModel<>();
        result.setData(this.usuarioService.listarUsuarios(username, nombre, apellido));
        return result;

    }

    @PostMapping("/users/")
    ResponseModel<Object> crearUsuario(@RequestBody UsuarioModel usuarioModel) {
        this.usuarioService.crearUsuario(usuarioModel);
        return ResponseModel.builder()
                .message("Usuario creado correctamente")
                .build();
    }

    @PutMapping("/users/{id}")
    ResponseModel<Object> modificarUsuario(@RequestBody UsuarioModel usuarioModel, @PathVariable("id") Long id) {
        this.usuarioService.modificarUsuario(usuarioModel, id);
        return ResponseModel.builder()
                .message("Usuario modificado correctamente")
                .build();
    }

    @DeleteMapping("/users/{id}")
    ResponseModel<Object> eliminarUsuario(@PathVariable("id") Long id) {
        this.usuarioService.eliminarUsuario(id);
        return ResponseModel.builder()
                .message("Usuario eliminado correctamente")
                .build();
    }

}
