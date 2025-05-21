package com.SAFE_Rescue.API_Administrador.repository;

import com.SAFE_Rescue.API_Administrador.modelo.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {


}

