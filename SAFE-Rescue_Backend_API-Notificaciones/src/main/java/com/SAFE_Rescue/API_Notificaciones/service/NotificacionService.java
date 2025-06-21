package com.SAFE_Rescue.API_Notificaciones.service;

import com.SAFE_Rescue.API_Notificaciones.modelo.Notificacion;
import com.SAFE_Rescue.API_Notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio que gestiona la lógica de negocio para las notificaciones.
 * Proporciona métodos para crear, leer, actualizar y eliminar notificaciones,
 * incluyendo validaciones específicas de negocio.
 */
@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    /**
     * Constructor del servicio de Notificaciones.
     * Inyecta el repositorio de notificaciones.
     *
     * @param notificacionRepository Repositorio para acceder a los datos de notificación.
     */
    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * Crea una nueva notificación en la base de datos.
     * La fecha y el estado de la notificación se establecen automáticamente.
     * Se requiere que la lista de receptores no esté vacía.
     *
     * @param notificacion El objeto Notificacion a guardar.
     * @return La notificación creada con su ID asignado.
     * @throws IllegalArgumentException Si la lista de receptores está vacía.
     */
    @Transactional
    public Notificacion crearNotificacion(Notificacion notificacion) {
        if (notificacion.getReceptores() == null || notificacion.getReceptores().isEmpty()) {
            throw new IllegalArgumentException("La lista de receptores no puede estar vacía al crear.");
        }
        notificacion.setFechaNotificacion(LocalDateTime.now());
        notificacion.setEstadoNotificacion(true);
        return notificacionRepository.save(notificacion);
    }

    /**
     * Obtiene una lista de todas las notificaciones existentes en la base de datos.
     *
     * @return Una lista de objetos Notificacion.
     */
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }

    /**
     * Obtiene una notificación específica por su ID.
     *
     * @param id El ID de la notificación a buscar.
     * @return Un {@link Optional} que contiene la notificación si se encuentra, o vacío si no.
     */
    public Optional<Notificacion> obtenerNotificacionPorId(int id) {
        return notificacionRepository.findById(id);
    }

    /**
     * Actualiza una notificación existente.
     * Permite la actualización parcial de campos como título, contenido, estado y receptores.
     * Realiza validaciones de longitud y no-vacío para los campos de texto.
     *
     * @param idNotificacion El ID de la notificación a actualizar.
     * @param datosActualizados Un objeto Notificacion con los campos a actualizar.
     * Solo los campos no nulos o no vacíos en este objeto serán aplicados.
     * @return La notificación actualizada.
     * @throws RuntimeException Si la notificación con el ID especificado no es encontrada.
     * @throws IllegalArgumentException Si el título o contenido están en blanco, o si exceden la longitud permitida,
     * o si la lista de receptores intenta quedar vacía al actualizar.
     */
    @Transactional
    public Notificacion actualizarNotificacion(int idNotificacion, Notificacion datosActualizados) {
        Notificacion notificacionExistente = notificacionRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + idNotificacion));

        if (datosActualizados.getTituloNotificacion() != null) {
            if (datosActualizados.getTituloNotificacion().isBlank()) {
                throw new IllegalArgumentException("El título no puede estar en blanco.");
            }
            if (datosActualizados.getTituloNotificacion().length() > 50) {
                throw new IllegalArgumentException("El título debe tener entre 1 y 50 caracteres.");
            }
            notificacionExistente.setTituloNotificacion(datosActualizados.getTituloNotificacion());
        }

        if (datosActualizados.getContenidoNotificacion() != null) {
            if (datosActualizados.getContenidoNotificacion().isBlank()) {
                throw new IllegalArgumentException("El contenido no puede estar en blanco.");
            }
            if (datosActualizados.getContenidoNotificacion().length() > 500) {
                throw new IllegalArgumentException("El contenido debe tener entre 1 y 500 caracteres.");
            }
            notificacionExistente.setContenidoNotificacion(datosActualizados.getContenidoNotificacion());
        }

        if (datosActualizados.getEstadoNotificacion() != null) {
            notificacionExistente.setEstadoNotificacion(datosActualizados.getEstadoNotificacion());
        }

        if (datosActualizados.getReceptores() != null && !datosActualizados.getReceptores().isEmpty()) {
            notificacionExistente.setReceptores(datosActualizados.getReceptores());
        }
        else if (datosActualizados.getReceptores() != null && datosActualizados.getReceptores().isEmpty()) {
            throw new IllegalArgumentException("La lista de receptores no puede quedar vacía al actualizar.");
        }

        return notificacionRepository.save(notificacionExistente);
    }

    /**
     * Elimina una notificación de la base de datos por su ID.
     *
     * @param id El ID de la notificación a eliminar.
     * @throws RuntimeException Si la notificación con el ID especificado no es encontrada.
     */
    @Transactional
    public void eliminarNotificacion(int id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada con ID: " + id + " para eliminar.");
        }
        notificacionRepository.deleteById(id);
    }
}