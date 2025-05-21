package com.SAFE_Rescue.API_Administrador.service;

import com.SAFE_Rescue.API_Administrador.repository.BomberoRepository;
import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Bombero save(Bombero bombero) {
        try {
            validarBombero(bombero);

            return bomberoRepository.save(bombero);
        } catch (Exception e) {

            throw new RuntimeException("Error al guardar el bombero: " + e.getMessage());
        }
    }

    public Bombero update(Bombero bombero, long id) {
        try {

            Bombero antiguoBombero = bomberoRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Bombero no encontrado"));

            //Control de errores
            if (bombero.getNombre() != null) {
                if (bombero.getNombre().length() > 50) {
                    throw new RuntimeException("El valor nombre excede máximo de caracteres (50)");
                }
                antiguoBombero.setNombre(bombero.getNombre());
            }

            if (bombero.getCorreo() != null) {
                if (bomberoRepository.existsByCorreo(bombero.getCorreo())) {
                    throw new RuntimeException("El Correo ya existe");
                }else{
                    if (bombero.getCorreo().length() > 80) {
                        throw new RuntimeException("El valor correo excede máximo de caracteres (80)");
                    }
                    antiguoBombero.setCorreo(bombero.getCorreo());
                }
            }

            if (bombero.getTelefono() != null) {
                if (bomberoRepository.existsByTelefono(bombero.getTelefono())) {
                    throw new RuntimeException("El Telefono ya existe");
                }else{
                    if (String.valueOf(bombero.getTelefono()).length()> 9) {
                        throw new RuntimeException("El valor telefono excede máximo de caracteres (9)");
                    }
                    antiguoBombero.setTelefono(bombero.getTelefono());
                }
            }

            if (bombero.getRun() != null) {
                if (bomberoRepository.existsByRun(bombero.getRun())) {
                    throw new RuntimeException("El RUN ya existe");
                }else{
                    if (String.valueOf(bombero.getRun()).length() > 8) {
                        throw new RuntimeException("El valor RUN excede máximo de caracteres (8)");
                    }
                    antiguoBombero.setRun(bombero.getRun());
                }
            }

            if (bombero.getDv() != null) {
                if (bombero.getDv().length() > 1) {
                    throw new RuntimeException("El valor DV excede máximo de caracteres (1)");
                }
                antiguoBombero.setDv(bombero.getDv());
            }

            if (bombero.getA_paterno() != null) {
                if (bombero.getA_paterno().length() > 50) {
                    throw new RuntimeException("El valor a_paterno excede máximo de caracteres (50)");
                }
                antiguoBombero.setA_paterno(bombero.getA_paterno());
            }

            if (bombero.getA_materno() != null) {
                if (bombero.getA_materno().length() > 50) {
                    throw new RuntimeException("El valor a_materno excede máximo de caracteres (50)");
                }
                antiguoBombero.setA_materno(bombero.getA_materno());
            }

            if (bombero.getFecha_registro() != null) {
                antiguoBombero.setFecha_registro(bombero.getFecha_registro());
            }


            return bomberoRepository.save(antiguoBombero);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el bombero: " + e.getMessage());
        }
    }

    public void delete(long id){
        try {
            bomberoRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar bombero: " + e.getMessage());
        }
    }

    public void validarBombero(@NotNull Bombero bombero) {

        if (bomberoRepository.existsByRun(bombero.getRun())) {
            throw new RuntimeException("El RUN ya existe");
        }

        if (bomberoRepository.existsByCorreo(bombero.getCorreo())) {
            throw new RuntimeException("El Correo ya existe");
        }

        if (bomberoRepository.existsByTelefono(bombero.getTelefono())) {
            throw new RuntimeException("El Telefono ya existe");
        }

        if (String.valueOf(bombero.getRun()).length() > 8) {
            throw new RuntimeException("El valor RUN excede máximo de caracteres (8)");
        }

        if (bombero.getDv().length() > 1) {
            throw new RuntimeException("El valor DV excede máximo de caracteres (1)");
        }

        if (bombero.getNombre().length() > 50) {
            throw new RuntimeException("El valor nombre excede máximo de caracteres (50)");
        }

        if (bombero.getA_paterno().length() > 50) {
            throw new RuntimeException("El valor a_paterno excede máximo de caracteres (50)");
        }

        if (bombero.getA_materno().length() > 50) {
            throw new RuntimeException("El valor a_materno excede máximo de caracteres (50)");
        }

        if (bombero.getCorreo().length() > 80) {
            throw new RuntimeException("El valor correo excede máximo de caracteres (80)");
        }

        if (String.valueOf(bombero.getTelefono()).length()> 9) {
            throw new RuntimeException("El valor telefono excede máximo de caracteres (9)");
        }

    }

}