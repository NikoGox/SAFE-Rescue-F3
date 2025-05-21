package com.SAFE_Rescue.API_Comunicacion.repository;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorradorMensajeRepository extends JpaRepository<BorradorMensaje, Integer> {

    public boolean existsByIdBrdrMensaje(int id_brdr_mensaje);

    public boolean existsByIdBrdrEmisor(int id_brdr_emisor);

}