package com.SAFE_Rescue.API_Comunicacion.repository;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link BorradorMensaje}.
 * Proporciona operaciones CRUD y algunas consultas personalizadas.
 */
@Repository
public interface BorradorMensajeRepository extends JpaRepository<BorradorMensaje, Integer> {

    /**
     * Verifica la existencia de un borrador por su ID.
     * @param idBrdrMensaje ID del borrador.
     * @return true si el borrador existe, false en caso contrario.
     */
    boolean existsByIdBrdrMensaje(int idBrdrMensaje);

    /**
     * Verifica la existencia de un borrador por el ID de su emisor.
     * @param idBrdrEmisor ID del emisor.
     * @return true si existe al menos un borrador de ese emisor, false en caso contrario.
     */
    boolean existsByIdBrdrEmisor(int idBrdrEmisor);
}