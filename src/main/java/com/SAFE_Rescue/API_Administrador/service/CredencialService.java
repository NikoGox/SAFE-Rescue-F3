package com.SAFE_Rescue.API_Administrador.service;

import com.SAFE_Rescue.API_Administrador.modelo.Rol;
import com.SAFE_Rescue.API_Administrador.repository.CredencialRepository;
import com.SAFE_Rescue.API_Administrador.modelo.Credencial;
import com.SAFE_Rescue.API_Administrador.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CredencialService {

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Credencial> findAll(){
        return credencialRepository.findAll();
    }

    public Credencial findByID(long id){
        return credencialRepository.findById(id).get();
    }


    public Credencial save(Credencial credencial) {
        try {
            Rol rol = rolRepository.findById(credencial.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            credencial.setRol(rol);

            return credencialRepository.save(credencial);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El correo ya está en uso. Por favor, use otro.");
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Error al guardar la credencial: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

    public Credencial update(Credencial credencial ,long id) {
        try {

            Credencial antiguaCredencial = credencialRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Credencial no encontrada"));

            if (credencial.getContrasenia() != null) {
                if (credencial.getContrasenia().length() > 16) {
                    throw new RuntimeException("El valor contrasenia excede máximo de caracteres (16)");
                }
                antiguaCredencial.setContrasenia(credencial.getContrasenia());
            }

            if (credencial.getCorreo() != null) {
                if (credencialRepository.existsByCorreo(credencial.getCorreo())) {
                    throw new RuntimeException("El Correo ya existe");
                }else{
                    if (credencial.getCorreo().length() > 80) {
                        throw new RuntimeException("El valor correo excede máximo de caracteres (80)");
                    }
                    antiguaCredencial.setCorreo(credencial.getCorreo());
                }
            }

            return credencialRepository.save(antiguaCredencial);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el bombero: " + e.getMessage());
        }
    }

    public void delete(long id){
        try {
            Credencial credencialVacia = credencialRepository.findById(id).get();

            credencialVacia.setContrasenia("");
            credencialVacia.setCorreo("");
            credencialVacia.setActivo(false);
            credencialVacia.setRol(null);
            credencialVacia.setIntentosFallidos(0);

            credencialRepository.save(credencialVacia);

        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar bombero: " + e.getMessage());
        }
    }

    public boolean verificarCredenciales(String correo, String contrasenia) {
        Credencial credencial = credencialRepository.findByCorreo(correo);
        if (credencial != null) {
            boolean sonCorrectas = contrasenia.equals(credencial.getContrasenia());
            if (!sonCorrectas) {
                credencial.setIntentosFallidos(credencial.getIntentosFallidos() + 1);
                credencialRepository.save(credencial);
            }
            return sonCorrectas;
        }
        return false;
    }


    public void asignarRol(long credencialId,int rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Credencial credencial = credencialRepository.findById(credencialId)
                .orElseThrow(() -> new RuntimeException("Credencial no encontrada"));

        credencial.setRol(rol);

        credencialRepository.save(credencial);
    }

}
