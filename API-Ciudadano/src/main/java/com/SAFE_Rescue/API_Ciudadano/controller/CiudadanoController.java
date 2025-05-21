package com.SAFE_Rescue.API_Ciudadano.controller;

import com.SAFE_Rescue.API_Ciudadano.service.CiudadanoService;
import com.SAFE_Rescue.API_Ciudadano.modelo.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api-ciudadano/v1/ciudadanos")
public class CiudadanoController {

    @Autowired
    private CiudadanoService ciudadanoService;

    @GetMapping
    public ResponseEntity<List<Ciudadano>> listar(){

        List<Ciudadano> ciudadanos = ciudadanoService.findAll();
        if(ciudadanos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(ciudadanos);
    }

    @PostMapping
    public ResponseEntity<String> agregarCiudadano(@RequestBody Ciudadano ciudadano) {
        try {

            ciudadanoService.validarCiudadano(ciudadano);
            Ciudadano nuevoCiudadano = ciudadanoService.save(ciudadano);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ciudadano creado con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCiudadano(@PathVariable long id) {
        Ciudadano ciudadano;

        try {
            ciudadano = ciudadanoService.findByID(id);
        }catch(NoSuchElementException e){
            return new ResponseEntity<String>("Ciudadano no encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(ciudadano);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCiudadano(@PathVariable long id, @RequestBody Ciudadano ciudadano) {
        try {
            Ciudadano nuevoCiudadano = ciudadanoService.update(ciudadano, id);
            return ResponseEntity.ok("Actualizado con éxito");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ciudadano no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCiudadano(@PathVariable long id) {

        try {
            ciudadanoService.delete(id);
            return ResponseEntity.ok("Ciudadano eliminado con éxito.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ciudadano no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor.");
        }
    }

    @PostMapping("/{ciudadanoId}/asignar-credencial/{credencialId}")
    public ResponseEntity<String> asignarCredencial(@PathVariable int ciudadanoId, @PathVariable int credencialId) {
        try {
            ciudadanoService.asignarCredencial(ciudadanoId, credencialId);
            return ResponseEntity.ok("Credencial asignada al Ciudadano exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
