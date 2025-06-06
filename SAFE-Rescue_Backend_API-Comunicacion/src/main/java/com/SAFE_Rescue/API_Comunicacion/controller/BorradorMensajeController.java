package com.SAFE_Rescue.API_Comunicacion.controller;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import com.SAFE_Rescue.API_Comunicacion.service.BorradorMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de Borradores de Mensajes.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar borradores.
 */
@RestController
@RequestMapping("/api-comunicacion/v1/borradores-mensajes")
public class BorradorMensajeController {

    private final BorradorMensajeService borradorMensajeService;

    /**
     * Constructor para inyección de dependencias del servicio de borradores.
     * @param borradorMensajeService El servicio que maneja la lógica de negocio de los borradores.
     */
    @Autowired
    public BorradorMensajeController(BorradorMensajeService borradorMensajeService) {
        this.borradorMensajeService = borradorMensajeService;
    }

    /**
     * Crea un nuevo borrador de mensaje.
     * @param borradorMensaje Objeto BorradorMensaje con los datos para crear.
     * @return El borrador creado con estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<BorradorMensaje> crearBorrador(@RequestBody BorradorMensaje borradorMensaje) {
        BorradorMensaje nuevoBorrador = borradorMensajeService.guardarBorrador(borradorMensaje);
        return new ResponseEntity<>(nuevoBorrador, HttpStatus.CREATED);
    }

    /**
     * Obtiene una lista de todos los borradores de mensajes.
     * @return Lista de borradores con estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<BorradorMensaje>> obtenerTodosLosBorradores() {
        List<BorradorMensaje> borradores = borradorMensajeService.obtenerTodosLosBorradores();
        return new ResponseEntity<>(borradores, HttpStatus.OK);
    }

    /**
     * Obtiene un borrador de mensaje por su ID.
     * @param id El ID del borrador a buscar.
     * @return El borrador encontrado con estado 200 OK, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BorradorMensaje> obtenerBorradorPorId(@PathVariable int id) {
        Optional<BorradorMensaje> borrador = borradorMensajeService.obtenerBorradorPorId(id);
        return borrador.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza un borrador de mensaje existente por su ID.
     * Permite la actualización parcial de ciertos campos (título, contenido).
     * @param idBorrador ID del borrador a actualizar.
     * @param datosActualizados Objeto BorradorMensaje con los datos a actualizar.
     * @return El borrador actualizado con estado 200 OK, o 404 Not Found si el borrador no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BorradorMensaje> actualizarBorrador(
            @PathVariable("id") int idBorrador,
            @RequestBody BorradorMensaje datosActualizados) {
        try {
            BorradorMensaje borradorActualizado = borradorMensajeService.actualizarBorrador(idBorrador, datosActualizados);
            return new ResponseEntity<>(borradorActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un borrador de mensaje por su ID.
     * @param id El ID del borrador a eliminar.
     * @return Estado 204 No Content si la eliminación es exitosa, o 404 Not Found si el borrador no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBorrador(@PathVariable int id) {
        try {
            borradorMensajeService.eliminarBorrador(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}