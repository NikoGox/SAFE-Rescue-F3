package com.SAFE_Rescue.API_Administrador.repository;

import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BomberoRepository extends JpaRepository<Bombero , Long> {

    public boolean existsByRun(int run);

    public boolean existsByCorreo(String correo);

    public boolean existsByTelefono(int telefono);

}