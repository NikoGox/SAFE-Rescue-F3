package com.SAFE_Rescue.API_Administrador.service;

import com.SAFE_Rescue.API_Administrador.repository.BomberoRepository;
import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BomberoService {

    @Autowired
    private BomberoRepository bomberoRepository;

    public List<Bombero> findAll(){
        return bomberoRepository.findAll();
    }

    public Bombero findByID(long id){
        return bomberoRepository.findById(id).get();
    }

    public Bombero save(Bombero bombero){

        if (bomberoRepository.existsByRun(bombero.getRun())) {
            throw new RuntimeException("El RUN ya existe");
        }

        if (bomberoRepository.existsByCorreo(bombero.getCorreo())) {
            throw new RuntimeException("El Correo ya existe");
        }

        if (bomberoRepository.existsByTelefono(bombero.getTelefono())) {
            throw new RuntimeException("El Telefono ya existe");
        }

        if (bombero.getRun()>8) {
            throw new RuntimeException("El valor run excede máximo de carácteres (8)");
        }

        if (bombero.getDv().length()>1) {
            throw new RuntimeException("El valor dv excede máximo de carácteres (1)");
        }

        if (bombero.getNombre().length()>50) {
            throw new RuntimeException("El valor nombre excede máximo de carácteres (50)");
        }

        if (bombero.getA_paterno().length()>50) {
            throw new RuntimeException("El valor a_paterno excede máximo de carácteres (50)");
        }

        if (bombero.getA_materno().length()>50) {
            throw new RuntimeException("El valor a_materno excede máximo de carácteres (50)");
        }

        if (bombero.getCorreo().length()>80) {
            throw new RuntimeException("El valor correo excede máximo de carácteres (80)");
        }

        if (bombero.getTelefono()>50) {
            throw new RuntimeException("El valor telefono excede máximo de carácteres (9)");
        }

        if (bombero.getContrasenia().length()>16) {
            throw new RuntimeException("El valor contrasenia excede máximo de carácteres (10)");
        }

        return bomberoRepository.save(bombero);
    }


    public void delete(long id){
        bomberoRepository.deleteById(id);
    }

}