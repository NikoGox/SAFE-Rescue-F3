package com.SAFE_Rescue.API_Notificaciones.controller;

import com.SAFE_Rescue.API_Notificaciones.modelo.Notificacion;
import com.SAFE_Rescue.API_Notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de notificaciones de emergencia.
 * Maneja las solicitudes HTTP relacionadas con la creación, obtención,
 * actualización y eliminación de notificaciones.
 */
@RestController
@RequestMapping("/api-notificaciones/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    /**
     * Constructor del controlador de Notificaciones.
     * Inyecta el servicio de notificaciones.
     *
     * @param notificacionService Servicio para gestionar las operaciones de negocio de las notificaciones.
     */
    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * Crea una nueva notificación.
     * Valida la entrada del cuerpo de la solicitud.
     *
     * @param notificacion El objeto Notificacion a crear.
     * @param result Resultado de la validación del binding.
     * @return ResponseEntity con la notificación creada y HttpStatus.CREATED (201),
     * o un mensaje de error y HttpStatus.BAD_REQUEST (400) si la validación falla
     * o hay un argumento inválido en el servicio.
     */
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@Valid @RequestBody Notificacion notificacion, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try {
            Notificacion nuevaNotificacion = notificacionService.crearNotificacion(notificacion);
            return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene una lista de todas las notificaciones.
     *
     * @return ResponseEntity con una lista de Notificacion y HttpStatus.OK (200).
     */
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    /**
     * Obtiene una notificación específica por su ID.
     *
     * @param id El ID de la notificación a buscar.
     * @return ResponseEntity con la Notificacion y HttpStatus.OK (200) si se encuentra,
     * o HttpStatus.NOT_FOUND (404) si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable int id) {
        Optional<Notificacion> notificacion = notificacionService.obtenerNotificacionPorId(id);
        return notificacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza una notificación existente por su ID.
     * Permite la actualización parcial de los campos y valida la entrada.
     *
     * @param idNotificacion El ID de la notificación a actualizar.
     * @param datosActualizados El objeto Notificacion con los datos a actualizar.
     * Debe cumplir con las validaciones de la entidad.
     * @param result Resultado de la validación del binding.
     * @return ResponseEntity con la notificación actualizada y HttpStatus.OK (200),
     * o un mensaje de error y HttpStatus.BAD_REQUEST (400) si la validación falla
     * o hay un argumento inválido en el servicio.
     * Retorna HttpStatus.NOT_FOUND (404) si la notificación no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNotificacion(@PathVariable("id") int idNotificacion,
                                                    @Valid @RequestBody Notificacion datosActualizados,
                                                    BindingResult result) {

        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            Notificacion notificacionActualizada = notificacionService.actualizarNotificacion(idNotificacion, datosActualizados);
            return new ResponseEntity<>(notificacionActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una notificación por su ID.
     *
     * @param id El ID de la notificación a eliminar.
     * @return ResponseEntity con HttpStatus.NO_CONTENT (204) si la eliminación es exitosa.
     * Retorna HttpStatus.NOT_FOUND (404) con un mensaje de error si la notificación no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable int id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}