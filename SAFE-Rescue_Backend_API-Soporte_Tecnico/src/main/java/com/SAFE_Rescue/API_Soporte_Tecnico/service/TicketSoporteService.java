package com.SAFE_Rescue.API_Soporte_Tecnico.service;

import com.SAFE_Rescue.API_Soporte_Tecnico.modelo.TicketSoporte;
import com.SAFE_Rescue.API_Soporte_Tecnico.repository.TicketSoporteRepository;
import com.SAFE_Rescue.API_Soporte_Tecnico.controller.TicketSoporteController.CrearTicketSoporteRequest;
import com.SAFE_Rescue.API_Soporte_Tecnico.controller.TicketSoporteController.ActualizarTicketSoporteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa la lógica de negocio para la gestión de Tickets de Soporte.
 * Proporciona métodos para operaciones CRUD y búsquedas.
 */
@Service
@Transactional
public class TicketSoporteService {

    private final TicketSoporteRepository ticketSoporteRepository;

    /**
     * Constructor para inyección de dependencias del repositorio de tickets.
     * @param ticketSoporteRepository El repositorio para interactuar con la base de datos.
     */
    @Autowired
    public TicketSoporteService(TicketSoporteRepository ticketSoporteRepository) {
        this.ticketSoporteRepository = ticketSoporteRepository;
    }

    /**
     * Crea un nuevo ticket de soporte.
     * @param ticketSoporte El objeto TicketSoporte a ser creado.
     * @return El ticket creado y guardado.
     * @throws RuntimeException Si alguna validación de negocio falla.
     */
    public TicketSoporte crearTicket(TicketSoporte ticketSoporte) {
        if (ticketSoporte.getFechaHoraCreacionTicket() == null) {
            ticketSoporte.setFechaHoraCreacionTicket(new Date());
        }
        validarTicketSoporte(ticketSoporte);
        return ticketSoporteRepository.save(ticketSoporte);
    }

    /**
     * Crea un nuevo ticket de soporte a partir de un DTO de solicitud.
     * @param request DTO con los datos del nuevo ticket.
     * @return El ticket creado.
     * @throws RuntimeException Si alguna validación de negocio falla.
     */
    public TicketSoporte crearTicketDesdeRequest(CrearTicketSoporteRequest request) {
        TicketSoporte ticketSoporte = new TicketSoporte();
        ticketSoporte.setEmisorTicketId(request.getEmisorTicketId());
        ticketSoporte.setTecnicoAsignadoId(request.getTecnicoAsignadoId());
        ticketSoporte.setAsunto(request.getAsunto());
        ticketSoporte.setDescripcion(request.getDescripcion());
        ticketSoporte.setCategoria(request.getCategoria());
        ticketSoporte.setPrioridad(request.getPrioridad());
        ticketSoporte.setEstado(request.getEstado());
        ticketSoporte.setFechaHoraCreacionTicket(new Date());

        validarTicketSoporte(ticketSoporte);
        return ticketSoporteRepository.save(ticketSoporte);
    }

    /**
     * Obtiene una lista de todos los tickets de soporte.
     * @return Lista de todos los tickets.
     */
    public List<TicketSoporte> obtenerTodosLosTickets() {
        return ticketSoporteRepository.findAll();
    }

    /**
     * Busca un ticket de soporte por su ID.
     * @param id El ID del ticket a buscar.
     * @return Un Optional que contiene el TicketSoporte si se encuentra.
     */
    public Optional<TicketSoporte> obtenerTicketPorId(Integer id) {
        return ticketSoporteRepository.findById(id);
    }

    /**
     * Actualiza un ticket de soporte existente.
     * @param id El ID del ticket a actualizar.
     * @param ticketSoporteActualizado El objeto TicketSoporte con los datos a actualizar.
     * @return Un Optional que contiene el ticket actualizado.
     * @throws RuntimeException Si alguna validación de negocio falla.
     */
    public Optional<TicketSoporte> actualizarTicket(Integer id, TicketSoporte ticketSoporteActualizado) {
        return ticketSoporteRepository.findById(id)
                .map(ticket -> {
                    ticket.setTecnicoAsignadoId(ticketSoporteActualizado.getTecnicoAsignadoId());
                    ticket.setAsunto(ticketSoporteActualizado.getAsunto());
                    ticket.setDescripcion(ticketSoporteActualizado.getDescripcion());
                    ticket.setCategoria(ticketSoporteActualizado.getCategoria());
                    ticket.setPrioridad(ticketSoporteActualizado.getPrioridad());
                    ticket.setEstado(ticketSoporteActualizado.getEstado());
                    ticket.setFechaHoraResolucion(ticketSoporteActualizado.getFechaHoraResolucion());
                    ticket.setSolucionDetalle(ticketSoporteActualizado.getSolucionDetalle());
                    validarTicketSoporte(ticket);
                    return ticketSoporteRepository.save(ticket);
                });
    }

    /**
     * Actualiza un ticket de soporte a partir de un DTO de solicitud.
     * Solo actualiza los campos presentes en el DTO.
     * @param id El ID del ticket a actualizar.
     * @param request DTO con los datos para la actualización.
     * @return Un Optional que contiene el ticket actualizado.
     * @throws RuntimeException Si alguna validación de negocio falla.
     */
    public Optional<TicketSoporte> actualizarTicketDesdeRequest(Integer id, ActualizarTicketSoporteRequest request) {
        return ticketSoporteRepository.findById(id)
                .map(ticket -> {
                    if (request.getTecnicoAsignadoId() != null) {
                        ticket.setTecnicoAsignadoId(request.getTecnicoAsignadoId());
                    }
                    if (request.getAsunto() != null) {
                        ticket.setAsunto(request.getAsunto());
                    }
                    if (request.getDescripcion() != null) {
                        ticket.setDescripcion(request.getDescripcion());
                    }
                    if (request.getCategoria() != null) {
                        ticket.setCategoria(request.getCategoria());
                    }
                    if (request.getPrioridad() != null) {
                        ticket.setPrioridad(request.getPrioridad());
                    }
                    if (request.getEstado() != null) {
                        ticket.setEstado(request.getEstado());
                    }
                    if (request.getFechaHoraResolucion() != null) {
                        ticket.setFechaHoraResolucion(request.getFechaHoraResolucion());
                    }
                    if (request.getSolucionDetalle() != null) {
                        ticket.setSolucionDetalle(request.getSolucionDetalle());
                    }

                    validarTicketSoporte(ticket);
                    return ticketSoporteRepository.save(ticket);
                });
    }

    /**
     * Elimina un ticket de soporte por su ID.
     * @param id El ID del ticket a eliminar.
     * @throws RuntimeException Si el ticket no se encuentra.
     */
    public void eliminarTicket(Integer id) {
        if (!ticketSoporteRepository.existsById(id)) {
            throw new RuntimeException("Ticket de Soporte con ID " + id + " no encontrado para eliminar.");
        }
        ticketSoporteRepository.deleteById(id);
    }

    /**
     * Busca tickets de soporte por el ID del emisor.
     * @param emisorId El ID del emisor.
     * @return Lista de tickets del emisor.
     */
    public List<TicketSoporte> buscarTicketsPorEmisor(Integer emisorId) {
        return ticketSoporteRepository.findByEmisorTicketId(emisorId);
    }

    /**
     * Busca tickets de soporte por el ID del técnico asignado.
     * @param tecnicoId El ID del técnico.
     * @return Lista de tickets del técnico.
     */
    public List<TicketSoporte> buscarTicketsPorTecnicoAsignado(Integer tecnicoId) {
        return ticketSoporteRepository.findByTecnicoAsignadoId(tecnicoId);
    }

    /**
     * Busca tickets de soporte por su estado.
     * @param estado El estado a buscar.
     * @return Lista de tickets con el estado.
     */
    public List<TicketSoporte> buscarTicketsPorEstado(String estado) {
        return ticketSoporteRepository.findByEstado(estado);
    }

    /**
     * Busca tickets de soporte por su categoría (ignorando mayúsculas/minúsculas).
     * @param categoria La categoría a buscar.
     * @return Lista de tickets que coinciden con la categoría.
     */
    public List<TicketSoporte> buscarTicketsPorCategoria(String categoria) {
        return ticketSoporteRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    /**
     * Busca tickets de soporte por su prioridad.
     * @param prioridad La prioridad a buscar.
     * @return Lista de tickets con la prioridad.
     */
    public List<TicketSoporte> buscarTicketsPorPrioridad(String prioridad) {
        return ticketSoporteRepository.findByPrioridad(prioridad);
    }

    /**
     * Busca tickets de soporte cuyo asunto contenga una palabra clave (ignorando mayúsculas/minúsculas).
     * @param keyword La palabra clave a buscar.
     * @return Lista de tickets cuyo asunto contiene la palabra clave.
     */
    public List<TicketSoporte> buscarTicketsPorAsuntoConteniendo(String keyword) {
        return ticketSoporteRepository.findByAsuntoContainingIgnoreCase(keyword);
    }

    /**
     * Valida los campos obligatorios de un ticket.
     * @param ticketSoporte El ticket a validar.
     * @throws RuntimeException Si el asunto o la descripción están vacíos o el asunto es muy largo.
     */
    private void validarTicketSoporte(TicketSoporte ticketSoporte) {
        if (ticketSoporte.getAsunto() == null || ticketSoporte.getAsunto().trim().isEmpty()) {
            throw new RuntimeException("El asunto del ticket no puede estar vacío.");
        }
        if (ticketSoporte.getDescripcion() == null || ticketSoporte.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción del ticket no puede estar vacía.");
        }
        if (ticketSoporte.getAsunto().length() > 255) {
            throw new RuntimeException("El asunto es demasiado largo.");
        }
    }
}