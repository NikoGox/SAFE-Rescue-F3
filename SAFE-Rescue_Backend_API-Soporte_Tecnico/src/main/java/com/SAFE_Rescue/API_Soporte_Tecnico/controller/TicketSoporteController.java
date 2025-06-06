package com.SAFE_Rescue.API_Soporte_Tecnico.controller;

import com.SAFE_Rescue.API_Soporte_Tecnico.modelo.TicketSoporte;
import com.SAFE_Rescue.API_Soporte_Tecnico.service.TicketSoporteService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.SAFE_Rescue.API_Soporte_Tecnico.controller.TicketSoporteController.CrearTicketSoporteRequest;
import static com.SAFE_Rescue.API_Soporte_Tecnico.controller.TicketSoporteController.ActualizarTicketSoporteRequest;

/**
 * Controlador REST para la gestión de Tickets de Soporte.
 * Proporciona endpoints para operaciones CRUD y búsquedas personalizadas.
 */
@RestController
@RequestMapping("api-soporte-tecnico/v1/tickets-soporte")
public class TicketSoporteController {

    private final TicketSoporteService ticketSoporteService;

    /**
     * Constructor para inyección de dependencias del servicio de tickets.
     * @param ticketSoporteService El servicio que maneja la lógica de negocio de los tickets.
     */
    @Autowired
    public TicketSoporteController(TicketSoporteService ticketSoporteService) {
        this.ticketSoporteService = ticketSoporteService;
    }

    /**
     * DTO para la creación de un nuevo ticket de soporte.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrearTicketSoporteRequest {
        private Integer emisorTicketId;
        private Integer tecnicoAsignadoId;
        private String asunto;
        private String descripcion;
        private String categoria;
        private String prioridad;
        private String estado;
    }

    /**
     * DTO para la actualización parcial de un ticket de soporte existente.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualizarTicketSoporteRequest {
        private Integer tecnicoAsignadoId;
        private String asunto;
        private String descripcion;
        private String categoria;
        private String prioridad;
        private String estado;
        private Date fechaHoraResolucion;
        private String solucionDetalle;
    }

    /**
     * Crea un nuevo ticket de soporte.
     * @param request Objeto con los datos del nuevo ticket.
     * @return El ticket creado con estado 201 Created.
     * @throws RuntimeException Si los datos de entrada son inválidos (400 Bad Request).
     * @throws Exception Para otros errores internos del servidor (500 Internal Server Error).
     */
    @PostMapping
    public ResponseEntity<?> crearTicket(@RequestBody CrearTicketSoporteRequest request) {
        try {
            TicketSoporte nuevoTicket = ticketSoporteService.crearTicketDesdeRequest(request);
            return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al crear el ticket.");
        }
    }

    /**
     * Obtiene una lista de todos los tickets de soporte.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no hay tickets.
     */
    @GetMapping
    public ResponseEntity<List<TicketSoporte>> obtenerTodosLosTickets() {
        List<TicketSoporte> tickets = ticketSoporteService.obtenerTodosLosTickets();
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Obtiene un ticket de soporte por su ID.
     * @param id El ID del ticket a buscar.
     * @return El ticket encontrado con estado 200 OK.
     * @throws RuntimeException Si el ticket no se encuentra (404 Not Found).
     * @throws Exception Para otros errores internos del servidor (500 Internal Server Error).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTicketPorId(@PathVariable Integer id) {
        try {
            TicketSoporte ticket = ticketSoporteService.obtenerTicketPorId(id)
                    .orElseThrow(() -> new RuntimeException("Ticket de Soporte con ID " + id + " no encontrado."));
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al obtener el ticket.");
        }
    }

    /**
     * Actualiza un ticket de soporte existente por su ID.
     * @param id El ID del ticket a actualizar.
     * @param request Objeto con los datos a actualizar.
     * @return El ticket actualizado con estado 200 OK.
     * @throws RuntimeException Si el ticket no existe (404 Not Found) o los datos son inválidos (400 Bad Request).
     * @throws Exception Para otros errores internos del servidor (500 Internal Server Error).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTicket(@PathVariable Integer id, @RequestBody ActualizarTicketSoporteRequest request) {
        try {
            TicketSoporte ticketActualizado = ticketSoporteService.actualizarTicketDesdeRequest(id, request)
                    .orElseThrow(() -> new RuntimeException("Ticket de Soporte con ID " + id + " no encontrado para actualizar."));
            return new ResponseEntity<>(ticketActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al actualizar el ticket.");
        }
    }

    /**
     * Elimina un ticket de soporte por su ID.
     * @param id El ID del ticket a eliminar.
     * @return Estado 204 No Content si la eliminación es exitosa.
     * @throws RuntimeException Si el ticket no existe (404 Not Found).
     * @throws Exception Para otros errores internos del servidor (500 Internal Server Error).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTicket(@PathVariable Integer id) {
        try {
            ticketSoporteService.eliminarTicket(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al eliminar el ticket.");
        }
    }

    /**
     * Busca tickets de soporte por el ID del emisor.
     * @param emisorId El ID del emisor.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/emisor/{emisorId}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorEmisor(@PathVariable Integer emisorId) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorEmisor(emisorId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca tickets de soporte por el ID del técnico asignado.
     * @param tecnicoId El ID del técnico.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorTecnicoAsignado(@PathVariable Integer tecnicoId) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorTecnicoAsignado(tecnicoId);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca tickets de soporte por su estado.
     * @param estado El estado a buscar.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorEstado(@PathVariable String estado) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorEstado(estado);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca tickets de soporte por su categoría (ignorando mayúsculas/minúsculas).
     * @param categoria La categoría a buscar.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorCategoria(@PathVariable String categoria) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorCategoria(categoria);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca tickets de soporte por su prioridad.
     * @param prioridad La prioridad a buscar.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorPrioridad(@PathVariable String prioridad) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorPrioridad(prioridad);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    /**
     * Busca tickets de soporte cuyo asunto contenga una palabra clave (ignorando mayúsculas/minúsculas).
     * @param keyword La palabra clave a buscar.
     * @return Lista de tickets con estado 200 OK, o 204 No Content si no se encuentran.
     */
    @GetMapping("/asunto-keyword/{keyword}")
    public ResponseEntity<List<TicketSoporte>> buscarTicketsPorAsuntoConteniendo(@PathVariable String keyword) {
        List<TicketSoporte> tickets = ticketSoporteService.buscarTicketsPorAsuntoConteniendo(keyword);
        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}