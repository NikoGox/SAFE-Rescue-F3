package com.SAFE_Rescue.API_Ciudadano.service;

import com.SAFE_Rescue.API_Ciudadano.modelo.Credencial;
import com.SAFE_Rescue.API_Ciudadano.repository.CiudadanoRepository;
import com.SAFE_Rescue.API_Ciudadano.modelo.Ciudadano;
import com.SAFE_Rescue.API_Ciudadano.repository.CredencialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CiudadanoService {

    @Autowired
    private CiudadanoRepository ciudadanoRepository;

    @Autowired
    private CredencialService credencialService;

    @Autowired
    private CredencialRepository credencialRepository;

    public List<Ciudadano> findAll(){
        return ciudadanoRepository.findAll();
    }

    public Ciudadano findByID(long id){
        return ciudadanoRepository.findById(id).get();
    }

    public Ciudadano save(Ciudadano ciudadano) {
        try {
            Credencial credencial = ciudadano.getCredencial();

            validarCiudadano(ciudadano);

            Credencial guardadaCredencial = credencialService.save(credencial);

            ciudadano.setCredencial(guardadaCredencial);

            return ciudadanoRepository.save(ciudadano);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error: el correo de la credencial ya está en uso.");
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Error al guardar el Ciudadano: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    public Ciudadano update(Ciudadano ciudadano, long id) {
        try {

            Ciudadano antiguoCiudadano = ciudadanoRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Ciudadano no encontrado"));

            //Control de errores
            if (ciudadano.getNombre() != null) {
                if (ciudadano.getNombre().length() > 50) {
                    throw new RuntimeException("El valor nombre excede máximo de caracteres (50)");
                }
                antiguoCiudadano.setNombre(ciudadano.getNombre());
            }

            if (ciudadano.getTelefono() != null) {
                if (ciudadanoRepository.existsByTelefono(ciudadano.getTelefono())) {
                    throw new RuntimeException("El Telefono ya existe");
                }else{
                    if (String.valueOf(ciudadano.getTelefono()).length()> 9) {
                        throw new RuntimeException("El valor telefono excede máximo de caracteres (9)");
                    }
                    antiguoCiudadano.setTelefono(ciudadano.getTelefono());
                }
            }

            if (ciudadano.getRun() != null) {
                if (ciudadanoRepository.existsByRun(ciudadano.getRun())) {
                    throw new RuntimeException("El RUN ya existe");
                }else{
                    if (String.valueOf(ciudadano.getRun()).length() > 8) {
                        throw new RuntimeException("El valor RUN excede máximo de caracteres (8)");
                    }
                    antiguoCiudadano.setRun(ciudadano.getRun());
                }
            }

            if (ciudadano.getDv() != null) {
                if (ciudadano.getDv().length() > 1) {
                    throw new RuntimeException("El valor DV excede máximo de caracteres (1)");
                }
                antiguoCiudadano.setDv(ciudadano.getDv());
            }

            if (ciudadano.getA_paterno() != null) {
                if (ciudadano.getA_paterno().length() > 50) {
                    throw new RuntimeException("El valor a_paterno excede máximo de caracteres (50)");
                }
                antiguoCiudadano.setA_paterno(ciudadano.getA_paterno());
            }

            if (ciudadano.getA_materno() != null) {
                if (ciudadano.getA_materno().length() > 50) {
                    throw new RuntimeException("El valor a_materno excede máximo de caracteres (50)");
                }
                antiguoCiudadano.setA_materno(ciudadano.getA_materno());
            }

            if (ciudadano.getFecha_registro() != null) {
                antiguoCiudadano.setFecha_registro(ciudadano.getFecha_registro());
            }


            return ciudadanoRepository.save(antiguoCiudadano);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el Ciudadano: " + e.getMessage());
        }
    }

    public void delete(long id){
        try {
            if (!ciudadanoRepository.existsById(id)) {
                throw new NoSuchElementException("Ciudadano no encontrado");
            }
            ciudadanoRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar Ciudadano: " + e.getMessage());
        }
    }

    public void validarCiudadano(@NotNull Ciudadano ciudadano) {

        if (ciudadanoRepository.existsByRun(ciudadano.getRun())) {
            throw new RuntimeException("El RUN ya existe");
        }

        if (ciudadanoRepository.existsByTelefono(ciudadano.getTelefono())) {
            throw new RuntimeException("El Telefono ya existe");
        }

        if (String.valueOf(ciudadano.getRun()).length() > 8) {
            throw new RuntimeException("El valor RUN excede máximo de caracteres (8)");
        }

        if (ciudadano.getDv().length() > 1) {
            throw new RuntimeException("El valor DV excede máximo de caracteres (1)");
        }

        if (ciudadano.getNombre().length() > 50) {
            throw new RuntimeException("El valor nombre excede máximo de caracteres (50)");
        }

        if (ciudadano.getA_paterno().length() > 50) {
            throw new RuntimeException("El valor a_paterno excede máximo de caracteres (50)");
        }

        if (ciudadano.getA_materno().length() > 50) {
            throw new RuntimeException("El valor a_materno excede máximo de caracteres (50)");
        }

        if (String.valueOf(ciudadano.getTelefono()).length()> 9) {
            throw new RuntimeException("El valor telefono excede máximo de caracteres (9)");
        }

    }

    public void asignarCredencial(long ciudadanoId, long credencialId) {
        Ciudadano ciudadano = ciudadanoRepository.findById(ciudadanoId)
                .orElseThrow(() -> new RuntimeException("Ciudadano no encontrado"));

        Credencial credencial = credencialRepository.findById(credencialId)
                .orElseThrow(() -> new RuntimeException("Credencial no encontrada"));

        ciudadano.setCredencial(credencial);
        ciudadanoRepository.save(ciudadano);
    }

}