package com.SAFE_Rescue.API_Administrador.controller;

import com.SAFE_Rescue.API_Administrador.modelo.Rol;
import com.SAFE_Rescue.API_Administrador.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles(){

        List<Rol> rol = rolService.findAllRoles();
        if(rol.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(rol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRol(@PathVariable int id) {
        Rol rol;

        try {
            rol = rolService.findByRol(id);
        }catch(NoSuchElementException e){
            return new ResponseEntity<String>("Credencial no encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rol);

    }

    @PostMapping
    public ResponseEntity<String> agregarRol(@RequestBody Rol rol) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("No posee permisos para crear roles.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarRol(@PathVariable long id, @RequestBody Rol rol) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("No posee permisos para realizar cambios.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("No posee permisos para realizar eliminar.");
    }

}