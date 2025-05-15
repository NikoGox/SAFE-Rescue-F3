package com.SAFE_Rescue.API_Administrador.service;

import com.SAFE_Rescue.API_Administrador.modelo.Rol;
import com.SAFE_Rescue.API_Administrador.repository.CredencialRepository;
import com.SAFE_Rescue.API_Administrador.modelo.Credencial;
import com.SAFE_Rescue.API_Administrador.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Credencial save(Credencial credencial, Rol rol) {
        try {
            if (!rolRepository.existsById(rol.getId())) {
                throw new RuntimeException("Rol no encontrado");
            }else{
                Credencial savedCredencial = credencialRepository.save(credencial);

                asignarRol(rol.getId(), savedCredencial.getId());
                return savedCredencial;
            }
        } catch (Exception e) {

            throw new RuntimeException("Error al guardar la credencial: " + e.getMessage());
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

    public boolean verificarCredenciales(String correo, String password) {
        Credencial credencial = credencialRepository.findByCorreo(correo);
        if (credencial != null) {
            return password.equals(credencial.getContrasenia());
        }
        return false;
    }

    public void asignarRol(int rolId, long credencialId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Credencial credencial = credencialRepository.findById(credencialId)
                .orElseThrow(() -> new RuntimeException("Credencial no encontrada"));

        credencial.setRol(rol);

        credencialRepository.save(credencial);
    }

}
