package com.SAFE_Rescue.API_Comunicacion.controller;

import com.SAFE_Rescue.API_Comunicacion.modelo.Mensaje;
import com.SAFE_Rescue.API_Comunicacion.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Controlador REST para la gestión de Mensajes enviados.
 * Proporciona endpoints para crear, obtener y eliminar mensajes.
 */
@RestController
@RequestMapping("/api-comunicacion/v1/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    /**
     * Constructor para inyección de dependencias del servicio de mensajes.
     * @param mensajeService El servicio que maneja la lógica de negocio de los mensajes.
     */
    @Autowired
    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /**
     * DTO para la solicitud de creación de un mensaje.
     * Contiene el ID del borrador original y el ID del receptor.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CrearMensajeRequest {
        private int idBorradorOriginal;
        private int idReceptor;
    }

    /**
     * Crea un nuevo mensaje a partir de un borrador.
     * @param request Objeto con el ID del borrador y el ID del receptor.
     * @return El mensaje creado con estado 201 Created.
     * @throws IllegalStateException Si el borrador ya fue enviado (aunque el servicio lo maneja como RuntimeException).
     * @throws RuntimeException Si el borrador no se encuentra o hay otros errores de negocio (400 Bad Request).
     */
    @PostMapping
    public ResponseEntity<Mensaje> crearMensaje(@RequestBody CrearMensajeRequest request) {
        try {
            Mensaje nuevoMensaje = mensajeService.crearMensajeDesdeBorradorYReceptor(
                    request.getIdBorradorOriginal(),
                    request.getIdReceptor()
            );
            return new ResponseEntity<>(nuevoMensaje, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Este catch podría ser más específico si el servicio lanzara IllegalStateException
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene una lista de todos los mensajes enviados.
     * @return Lista de mensajes con estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Mensaje>> obtenerTodosLosMensajes() {
        List<Mensaje> mensajes = mensajeService.obtenerTodosLosMensajes();
        return new ResponseEntity<>(mensajes, HttpStatus.OK);
    }

    /**
     * Obtiene un mensaje por su ID.
     * @param id El ID del mensaje a buscar.
     * @return El mensaje encontrado con estado 200 OK, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerMensajePorId(@PathVariable int id) {
        Optional<Mensaje> mensaje = mensajeService.obtenerMensajePorId(id);
        return mensaje.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina un mensaje por su ID.
     * @param id El ID del mensaje a eliminar.
     * @return Estado 204 No Content si la eliminación es exitosa, o 404 Not Found si el mensaje no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable int id) {
        try {
            mensajeService.eliminarMensaje(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}