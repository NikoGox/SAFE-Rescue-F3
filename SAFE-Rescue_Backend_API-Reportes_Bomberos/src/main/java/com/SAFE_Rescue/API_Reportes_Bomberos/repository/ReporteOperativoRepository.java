package com.SAFE_Rescue.API_Reportes_Bomberos.repository;

import com.SAFE_Rescue.API_Reportes_Bomberos.modelo.ReporteOperativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repositorio para la entidad {@link ReporteOperativo}.
 * Proporciona operaciones CRUD y define consultas personalizadas de Spring Data JPA.
 */
@Repository // Marca esta interfaz como un componente de repositorio de Spring
public interface ReporteOperativoRepository extends JpaRepository<ReporteOperativo, Integer> {

    /**
     * Busca reportes por el ID del incidente asociado.
     * @param idIncidente ID del incidente.
     * @return Lista de reportes operativos que coinciden con el ID del incidente.
     */
    List<ReporteOperativo> findByIdIncidente(Integer idIncidente);

    /**
     * Busca reportes por el estado de la operación.
     * @param estadoOperacion Estado de la operación (ej: "Finalizada", "En Curso").
     * @return Lista de reportes operativos que coinciden con el estado.
     */
    List<ReporteOperativo> findByEstadoOperacion(String estadoOperacion);

    /**
     * Busca reportes creados después de una fecha y hora específica.
     * @param fechaHora Fecha y hora a partir de la cual buscar.
     * @return Lista de reportes operativos creados después de la fecha y hora dada.
     */
    List<ReporteOperativo> findByFechaHoraReporteAfter(Date fechaHora);

    /**
     * Busca reportes por el ID del bombero que emitió el reporte.
     * @param bomberoEmisorId ID del bombero emisor.
     * @return Lista de reportes operativos emitidos por el bombero con el ID especificado.
     */
    List<ReporteOperativo> findByBomberoEmisorId(Integer bomberoEmisorId);

    /**
     * Busca reportes con un número de heridos mayor o igual al especificado.
     * @param numHeridos Número mínimo de heridos.
     * @return Lista de reportes operativos que cumplen la condición.
     */
    List<ReporteOperativo> findByNumHeridosGreaterThanEqual(Integer numHeridos);

    /**
     * Busca reportes con un número de fallecidos mayor o igual al especificado.
     * @param numFallecidos Número mínimo de fallecidos.
     * @return Lista de reportes operativos que cumplen la condición.
     */
    List<ReporteOperativo> findByNumFallecidosGreaterThanEqual(Integer numFallecidos);

    /**
     * Busca reportes con un número de desaparecidos mayor o igual al especificado.
     * @param numDesaparecidos Número mínimo de desaparecidos.
     * @return Lista de reportes operativos que cumplen la condición.
     */
    List<ReporteOperativo> findByNumDesaparecidosGreaterThanEqual(Integer numDesaparecidos);

    /**
     * Busca reportes cuya causa probable contenga una cadena de texto, ignorando mayúsculas y minúsculas.
     * @param causaProbable Palabra clave o frase a buscar en la causa probable.
     * @return Lista de reportes operativos que contienen la causa probable.
     */
    List<ReporteOperativo> findByCausaProbableContainingIgnoreCase(String causaProbable);

    /**
     * Busca reportes por ID de incidente y estado de operación.
     * @param idIncidente ID del incidente.
     * @param estadoOperacion Estado de la operación.
     * @return Lista de reportes operativos que coinciden con ambos criterios.
     */
    List<ReporteOperativo> findByIdIncidenteAndEstadoOperacion(Integer idIncidente, String estadoOperacion);

    /**
     * Busca reportes cuya duración de operación esté entre un rango de minutos.
     * @param minMinutos Duración mínima en minutos.
     * @param maxMinutos Duración máxima en minutos.
     * @return Lista de reportes operativos que están dentro del rango de duración.
     */
    List<ReporteOperativo> findByDuracionOperacionMinutosBetween(Integer minMinutos, Integer maxMinutos);

    /**
     * Busca reportes cuyo tipo de operación contenga una cadena de texto, ignorando mayúsculas y minúsculas.
     * @param tipoOperacion Palabra clave o frase a buscar en el tipo de operación.
     * @return Lista de reportes operativos que contienen el tipo de operación.
     */
    List<ReporteOperativo> findByTipoOperacionContainingIgnoreCase(String tipoOperacion);
}