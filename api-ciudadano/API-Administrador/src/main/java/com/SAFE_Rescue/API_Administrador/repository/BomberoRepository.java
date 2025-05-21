package com.SAFE_Rescue.API_Administrador.repository;

import com.SAFE_Rescue.API_Administrador.modelo.Bombero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BomberoRepository extends JpaRepository<Bombero , Long> {

    public boolean existsByRun(Long run);

    public boolean existsByCorreo(String correo);

    public boolean existsByTelefono(Long telefono);

}