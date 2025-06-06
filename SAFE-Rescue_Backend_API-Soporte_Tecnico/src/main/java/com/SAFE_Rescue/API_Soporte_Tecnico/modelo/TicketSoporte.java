package com.SAFE_Rescue.API_Soporte_Tecnico.modelo; // Asegúrate que el paquete sea el correcto para este microservicio

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "tickets_soporte") // Nombre de la tabla en la base de datos
public class TicketSoporte {

    @Id // Marca el campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de ID
    @Column(name = "id_ticket") // Mapea el campo a la columna de la tabla
    private Integer idTicket; // ID único del ticket de soporte

    @Column(name = "emisor_ticket_id", nullable = false)
    private Integer emisorTicketId; // ID del emisor del ticket (bombero o ciudadano)

    // El ID del técnico asignado, sin una entidad Tecnico separada en este microservicio.
    // Simplemente almacenamos el ID. Puede ser nulo si no hay técnico asignado.
    @Column(name = "tecnico_asignado_id", nullable = true) // No es nullable = false, permite NULL
    private Integer tecnicoAsignadoId; // ID del técnico de soporte asignado (puede ser NULL)

    @Column(name = "fec_hora_creacion_ticket", nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // Almacena la fecha y hora completa
    private Date fechaHoraCreacionTicket; // Fecha y hora en que se creó el ticket

    @Column(name = "asunto", length = 255, nullable = false)
    private String asunto; // Breve descripción/título del problema

    @Column(name = "descripcion", length = 2000, nullable = false)
    private String descripcion; // Descripción detallada del problema

    @Column(name = "categoria", length = 100, nullable = false)
    private String categoria; // Clasificación del tipo de problema (ej: "Software", "Hardware", "Conectividad")

    @Column(name = "prioridad", length = 50, nullable = false)
    private String prioridad; // Nivel de urgencia (ej: "Baja", "Media", "Alta", "Crítica")

    @Column(name = "estado", length = 50, nullable = false)
    private String estado; // Estado actual del ticket (ej: "Abierto", "En Progreso", "Resuelto")

    @Column(name = "fec_hora_resolucion") // No es nullable = false, permite NULL
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraResolucion; // Fecha y hora en que se resolvió el ticket

    @Column(name = "solucion_detalle", length = 2000) // No es nullable = false, permite NULL
    private String solucionDetalle; // Descripción de la solución implementada
}