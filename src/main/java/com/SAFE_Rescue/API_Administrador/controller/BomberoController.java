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

    @ResponseStatus(HttpStatus.CREATED)//Colocar el codigo 201
    @PostMapping
    public ResponseEntity<Bombero> agregarBombero(@RequestBody Bombero bombero){
        Bombero nuevoBombero = bomberoService.save(bombero);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoBombero);
    }

    @GetMapping("/{id}")
    public Bombero buscarBombero(@PathVariable long id){
        return bomberoService.findByID(id);
    }

    @PutMapping()
    public Bombero actualizarBombero(@RequestBody Bombero bombero){
        return bomberoService.save(bombero);
    }

    @DeleteMapping("/{id}")
    public void eliminarBombero(@PathVariable long id){
        bomberoService.delete(id);
    }


}