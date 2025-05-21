package com.SAFE_Rescue.API_Administrador.service;
import com.SAFE_Rescue.API_Administrador.modelo.Rol;
import com.SAFE_Rescue.API_Administrador.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    public Rol findByRol(int id){
        return rolRepository.findById(id).get();
    }


}
