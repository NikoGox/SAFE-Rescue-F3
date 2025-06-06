package com.SAFE_Rescue.API_Comunicacion.repository;

import com.SAFE_Rescue.API_Comunicacion.modelo.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Mensaje}.
 * Proporciona operaciones CRUD básicas.
 */
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    // No se necesitan métodos personalizados aquí, JpaRepository cubre las operaciones básicas.
}