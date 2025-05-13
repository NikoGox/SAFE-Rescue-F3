package com.SAFE_Rescue.API_Administrador.controller;

import com.SAFE_Rescue.API_Administrador.service.BomberoService;
import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bomberos")
public class BomberoController {

    @Autowired
    private BomberoService bomberoService;

    @GetMapping
    public ResponseEntity<List<Bombero>> listar(){

        List<Bombero> bomberos = bomberoService.findAll();
        if(bomberos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(bomberos);
    }

    @PostMapping
    public ResponseEntity<String> agregarBombero(@RequestBody Bombero bombero) {
        try {
            bomberoService.validarBombero(bombero);
            Bombero nuevoBombero = bomberoService.save(bombero);
            return ResponseEntity.status(HttpStatus.CREATED).body("Bombero creado con éxito.");
        } catch (RuntimeException e) {
            // Captura la excepción y devuelve el mensaje de error del servicio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // Devuelve el mensaje de error
        } catch (Exception e) {
            // Manejar otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }

    @GetMapping("/{id}")
    public Bombero buscarBombero(@PathVariable long id){
        return bomberoService.findByID(id);
    }

    @PutMapping("/{id}")
    public Bombero actualizarBombero(@RequestBody Bombero bombero){
        return bomberoService.save(bombero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBombero(@PathVariable long id) {
        bomberoService.delete(id);
        return ResponseEntity.ok("Bombero eliminado con éxito.");
    }
}