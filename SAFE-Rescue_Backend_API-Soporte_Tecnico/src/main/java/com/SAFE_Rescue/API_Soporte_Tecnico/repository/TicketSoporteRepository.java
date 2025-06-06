package com.SAFE_Rescue.API_Soporte_Tecnico.repository;

import com.SAFE_Rescue.API_Soporte_Tecnico.modelo.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para la entidad {@link TicketSoporte}.
 * Proporciona operaciones CRUD y define consultas personalizadas.
 */
@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Integer> {

    /**
     * Busca tickets por el ID del emisor.
     * @param emisorTicketId ID del emisor.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByEmisorTicketId(Integer emisorTicketId);

    /**
     * Busca tickets por el ID del técnico asignado.
     * @param tecnicoAsignadoId ID del técnico.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByTecnicoAsignadoId(Integer tecnicoAsignadoId);

    /**
     * Busca tickets por estado.
     * @param estado Estado del ticket.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByEstado(String estado);

    /**
     * Busca tickets por categoría (case-insensitive).
     * @param categoria Categoría a buscar.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByCategoriaContainingIgnoreCase(String categoria);

    /**
     * Busca tickets por prioridad.
     * @param prioridad Prioridad a buscar.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByPrioridad(String prioridad);

    /**
     * Busca tickets creados después de una fecha/hora.
     * @param fecha Fecha a partir de la cual buscar.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByFechaHoraCreacionTicketAfter(Date fecha);

    /**
     * Busca tickets resueltos después de una fecha/hora.
     * @param fecha Fecha a partir de la cual buscar.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByFechaHoraResolucionAfter(Date fecha);

    /**
     * Busca tickets por asunto que contenga una palabra clave (case-insensitive).
     * @param asunto Palabra clave en el asunto.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByAsuntoContainingIgnoreCase(String asunto);

    /**
     * Busca tickets por estado Y categoría.
     * @param estado Estado del ticket.
     * @param categoria Categoría del ticket.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByEstadoAndCategoria(String estado, String categoria);

    /**
     * Busca tickets por emisor y estado.
     * @param emisorTicketId ID del emisor.
     * @param estado Estado del ticket.
     * @return Lista de tickets.
     */
    List<TicketSoporte> findByEmisorTicketIdAndEstado(Integer emisorTicketId, String estado);
}