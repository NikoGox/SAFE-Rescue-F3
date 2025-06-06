package com.SAFE_Rescue.API_Capacitaciones.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data // Genera automáticamente getters, setters, toString(), equals() y hashCode()
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Entity // Indica que esta clase es una entidad JPA, mapeándose a una tabla en la base de datos
@Table(name = "recursos_educativos") // Especifica el nombre de la tabla en la base de datos
public class RecursoEducativo {


    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la estrategia de generación de valores de ID (autoincremental)
    @Column(name = "id_recurso_ed") // Mapea este campo a la columna 'id_recurso_ed' en la tabla
    private Integer idRecursoEd;

    @Column(name = "tipo_recurso_ed", length = 50, nullable = false) // Mapea a 'tipo_recurso_ed', longitud max 50, no nulo
    private String tipoRecursoEd;

    @Column(name = "nombre", length = 100, nullable = false) // Mapea a 'nombre', longitud max 100, no nulo
    private String nombre;

    @Column(name = "descripcion", length = 1000) // Mapea a 'descripcion', longitud max 1000, opcional
    private String descripcion;

    @Column(name = "autor", length = 100, nullable = false) // Mapea a 'autor', longitud max 100, no nulo
    private String autor;

    @Column(name = "fecha_publicacion_autor", nullable = false) // Mapea a 'fecha_publicacion_autor', no nulo
    @Temporal(TemporalType.TIMESTAMP) // Especifica que el tipo de dato en DB es un timestamp (fecha y hora completa)
    private Date fechaPublicacionAutor;

    @Column(name = "url", length = 500, nullable = false) // Mapea a 'url', longitud max 500, no nulo
    private String url;

    @Column(name = "fecha_creacion_recurso", nullable = false) // Mapea a 'fecha_creacion_recurso', no nulo
    @Temporal(TemporalType.TIMESTAMP) // Especifica que el tipo de dato en DB es un timestamp (fecha y hora completa)
    private Date fechaCreacionRecurso;

}