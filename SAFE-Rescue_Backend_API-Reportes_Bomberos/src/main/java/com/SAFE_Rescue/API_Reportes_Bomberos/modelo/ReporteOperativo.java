package com.SAFE_Rescue.API_Reportes_Bomberos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entidad que representa un Reporte Operativo en la base de datos.
 * Utiliza Lombok para la generación automática de getters, setters, constructores, etc.
 */
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "reportes_operativos") // Mapea la entidad a la tabla 'reportes_operativos' en la DB
public class ReporteOperativo {

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática del ID
    @Column(name = "id_reporte_operativo") // Mapea a la columna 'id_reporte_operativo'
    private Integer idReporteOperativo;

    @Column(name = "id_incidente", nullable = false) // Columna 'id_incidente', no puede ser nula a nivel de DB
    private Integer idIncidente;

    @Column(name = "fecha_hora_reporte", nullable = false) // Columna 'fecha_hora_reporte', no puede ser nula a nivel de DB
    @Temporal(TemporalType.TIMESTAMP) // Especifica que el tipo de dato es un timestamp (fecha y hora completa)
    private Date fechaHoraReporte;

    @Column(name = "tipo_operacion", length = 100, nullable = false) // Columna 'tipo_operacion', longitud max 100, no nula
    private String tipoOperacion;

    @Column(name = "estado_operacion", length = 50, nullable = false) // Columna 'estado_operacion', longitud max 50, no nula
    private String estadoOperacion;

    @Column(name = "duracion_operacion_minutos") // Columna 'duracion_operacion_minutos', opcional
    private Integer duracionOperacionMinutos;

    @Column(name = "num_heridos") // Columna 'num_heridos', opcional
    private Integer numHeridos;

    @Column(name = "num_fallecidos") // Columna 'num_fallecidos', opcional
    private Integer numFallecidos;

    @Column(name = "num_evacuados") // Columna 'num_evacuados', opcional
    private Integer numEvacuados;

    @Column(name = "num_desaparecidos") // Columna 'num_desaparecidos', opcional
    private Integer numDesaparecidos;

    @Column(name = "recursos_utilizados_detalle", length = 1000) // Columna 'recursos_utilizados_detalle', longitud max 1000, opcional
    private String recursosUtilizadosDetalle;

    @Column(name = "equipo_participante", length = 1000) // Columna 'equipo_participante', longitud max 1000, opcional
    private String equipoParticipante;

    @Column(name = "causa_probable", length = 255) // Columna 'causa_probable', longitud max 255, opcional
    private String causaProbable;

    @Column(name = "observaciones_adicionales", length = 2000) // Columna 'observaciones_adicionales', longitud max 2000, opcional
    private String observacionesAdicionales;

    @Column(name = "bombero_emisor_id", nullable = false) // Columna 'bombero_emisor_id', no puede ser nula
    private Integer bomberoEmisorId;
}