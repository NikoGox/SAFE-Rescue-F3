package com.SAFE_Rescue.API_Ciudadano.repository;

import com.SAFE_Rescue.API_Ciudadano.modelo.Ciudadano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadanoRepository extends JpaRepository<Ciudadano, Long> {

    public boolean existsByRun(Long run);

    public boolean existsByTelefono(Long telefono);

}