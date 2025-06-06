package com.SAFE_Rescue.API_Notificaciones.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase de modelo que representa una notificación de emergencia en el sistema SAFE Rescue.
 * Mapea a la tabla "notificacion_emergencia" en la base de datos.
 */
@Entity
@Table(name = "notificacion_emergencia")
@Data // Genera getters, setters, toString, equals y hashCode de Lombok
@NoArgsConstructor // Genera un constructor sin argumentos de Lombok
@AllArgsConstructor // Genera un constructor con todos los argumentos de Lombok
public class Notificacion {

    /**
     * Identificador único de la notificación.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private int idNotificacion;

    /**
     * Identificador del emisor de la notificación.
     * No puede ser nulo.
     */
    @NotNull(message = "El ID del emisor no puede ser nulo")
    @Column(name = "id_emisor", nullable = false)
    private Integer idEmisor;

    /**
     * Título de la notificación.
     * No puede estar en blanco y debe tener entre 1 y 50 caracteres.
     */
    @NotBlank(message = "El título no puede estar en blanco")
    @Size(min = 1, max = 50, message = "El título debe tener entre 1 y 50 caracteres")
    @Column(name = "titulo_notificacion", length = 50, nullable = false)
    private String tituloNotificacion;

    /**
     * Contenido detallado de la notificación.
     * No puede estar en blanco y debe tener entre 1 y 500 caracteres.
     */
    @NotBlank(message = "El contenido no puede estar en blanco")
    @Size(min = 1, max = 500, message = "El contenido debe tener entre 1 y 500 caracteres")
    @Column(name = "contenido_notificacion", length = 500, nullable = false)
    private String contenidoNotificacion;

    /**
     * Fecha y hora en que la notificación fue creada.
     * Se establece automáticamente al crear la notificación.
     */
    @Column(name = "fecha_notificacion", nullable = false)
    private LocalDateTime fechaNotificacion;

    /**
     * Estado de la notificación (por ejemplo, activa/inactiva, leída/no leída).
     * Se establece automáticamente como true al crear.
     */
    @Column(name = "estado_notificacion", nullable = false)
    private Boolean estadoNotificacion;

    /**
     * Lista de identificadores de los usuarios receptores de la notificación.
     * No puede estar vacía. Se mapea a una tabla de colección separada.
     */
    @NotEmpty(message = "La lista de receptores no puede estar vacía")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "notificacion_receptores", joinColumns = @JoinColumn(name = "id_notificacion"))
    @Column(name = "id_receptor")
    private List<Integer> receptores;

    /**
     * Constructor para crear una nueva notificación con los campos esenciales.
     * La fecha y el estado se inicializan automáticamente.
     *
     * @param idEmisor El ID del usuario que emite la notificación.
     * @param tituloNotificacion El título de la notificación.
     * @param contenidoNotificacion El contenido de la notificación.
     * @param receptores Una lista de IDs de los usuarios receptores de la notificación.
     */
    public Notificacion(Integer idEmisor, String tituloNotificacion, String contenidoNotificacion, List<Integer> receptores) {
        this.idEmisor = idEmisor;
        this.tituloNotificacion = tituloNotificacion;
        this.contenidoNotificacion = contenidoNotificacion;
        this.fechaNotificacion = LocalDateTime.now();
        this.estadoNotificacion = true;
        this.receptores = receptores;
    }
}