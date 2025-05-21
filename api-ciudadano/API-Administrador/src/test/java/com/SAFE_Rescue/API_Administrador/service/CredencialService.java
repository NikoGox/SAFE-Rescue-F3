package com.SAFE_Rescue.API_Administrador.service;

import com.SAFE_Rescue.API_Administrador.repository.CredencialRepository;
import com.SAFE_Rescue.API_Administrador.modelo.Credencial;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CredencialService {

    @Autowired
    private CredencialRepository credencialRepository;

    public List<Credencial> findAll(){
        return credencialRepository.findAll();
    }

    public Credencial findByID(long id){
        return credencialRepository.findById(id).get();
    }

    public Credencial save(Credencial credencial) {
        try {
            validarCredencial(credencial);

            return credencialRepository.save(credencial);
        } catch (Exception e) {

            throw new RuntimeException("Error al guardar la credencial: " + e.getMessage());
        }
    }

    public Credencial update(Credencial credencial ,long id) {
        try {

            Credencial antiguaCredencial = credencialRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Credencial no encontrada"));

            //Control de errores
            if (credencial.getContrasenia() != null) {
                if (credencial.getContrasenia().length() > 16) {
                    throw new RuntimeException("El valor contrasenia excede máximo de caracteres (16)");
                }
                antiguaCredencial.setContrasenia(credencial.getContrasenia());
            }


            return credencialRepository.save(antiguaCredencial);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el bombero: " + e.getMessage());
        }
    }

    public void delete(long id){
        try {
            credencialRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar bombero: " + e.getMessage());
        }
    }

    public void validarCredencial(@NotNull Credencial credencial) {

    }


}
