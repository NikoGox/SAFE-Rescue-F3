package com.SAFE_Rescue.API_Administrador.controller;

import com.SAFE_Rescue.API_Administrador.service.BomberoService;
import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarBombero(@PathVariable long id) {
        Bombero bombero;

        try {
            bombero = bomberoService.findByID(id);
        }catch(NoSuchElementException e){
            return new ResponseEntity<String>("Bombero no encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(bombero);

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarBombero(@PathVariable long id, @RequestBody Bombero bombero) {
        try {
            Bombero nuevoBombero = bomberoService.update(bombero, id);
            return ResponseEntity.ok("Actualizado con éxito");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Bombero no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBombero(@PathVariable long id) {

        bomberoService.delete(id);
        return ResponseEntity.ok("Bombero eliminado con éxito.");
    }

    @PostMapping("/{bomberoId}/asignar-credencial/{credencialId}")
    public ResponseEntity<String> asignarCredencial(@PathVariable int bomberoId, @PathVariable int credencialId) {
        try {
            bomberoService.asignarCredencial(bomberoId, credencialId);
            return ResponseEntity.ok("Credencial asignada al bombero exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}