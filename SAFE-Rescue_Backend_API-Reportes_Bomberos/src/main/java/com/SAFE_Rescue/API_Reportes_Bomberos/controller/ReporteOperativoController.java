package com.SAFE_Rescue.API_Reportes_Bomberos.controller;

import com.SAFE_Rescue.API_Reportes_Bomberos.modelo.ReporteOperativo;
import com.SAFE_Rescue.API_Reportes_Bomberos.service.ReporteOperativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de Reportes Operativos.
 * Proporciona endpoints para operaciones CRUD y búsquedas personalizadas.
 */
@RestController
@RequestMapping("api-reportes-bomberos/v1/reportes-operativos")
public class ReporteOperativoController {

    private final ReporteOperativoService reporteOperativoService;

    /**
     * Constructor para inyección de dependencias del servicio de reportes operativos.
     * @param reporteOperativoService El servicio que maneja la lógica de negocio de los reportes.
     */
    @Autowired
    public ReporteOperativoController(ReporteOperativoService reporteOperativoService) {
        this.reporteOperativoService = reporteOperativoService;
    }

    /**
     * Crea un nuevo reporte operativo.
     *
     * @param reporte El reporte a crear.
     * @return ResponseEntity con el reporte creado (201 CREATED) o un error (400 BAD_REQUEST, 500 INTERNAL_SERVER_ERROR).
     */
    @PostMapping
    public ResponseEntity<?> crearReporteOperativo(@RequestBody ReporteOperativo reporte) {
        try {
            ReporteOperativo nuevoReporte = reporteOperativoService.crearReporte(reporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
        } catch (IllegalArgumentException e) {
            // Captura las excepciones de validación lanzadas por el servicio (validaciones manuales)
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 500 Internal Server Error
        }
    }

    /**
     * Obtiene todos los reportes operativos.
     *
     * @return ResponseEntity con la lista de reportes (200 OK).
     */
    @GetMapping
    public ResponseEntity<List<ReporteOperativo>> obtenerTodosLosReportes() {
        List<ReporteOperativo> reportes = reporteOperativoService.obtenerTodosLosReportes();
        return ResponseEntity.ok(reportes);
    }

    /**
     * Obtiene un reporte operativo por su ID.
     *
     * @param id ID del reporte.
     * @return ResponseEntity con el reporte (200 OK) o 404 NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReporteOperativo> obtenerReportePorId(@PathVariable Integer id) {
        Optional<ReporteOperativo> reporte = reporteOperativoService.obtenerReportePorId(id);
        return reporte.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un reporte operativo existente.
     *
     * @param id ID del reporte a actualizar.
     * @param datosActualizados Objeto con los datos de actualización.
     * @return ResponseEntity con el reporte actualizado (200 OK) o un error (400 BAD_REQUEST, 404 NOT_FOUND, 500 INTERNAL_SERVER_ERROR).
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReporte(@PathVariable Integer id, @RequestBody ReporteOperativo datosActualizados) {
        try {
            Optional<ReporteOperativo> reporteActualizado = reporteOperativoService.actualizarReporte(id, datosActualizados);
            return reporteActualizado.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            // Captura las excepciones de validación lanzadas por el servicio
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        } catch (RuntimeException e) {
            // Captura otras excepciones de tiempo de ejecución del servicio (ej. "no encontrado" al eliminar)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Generalmente 404 Not Found
        } catch (Exception e) {
            // Cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // 500 Internal Server Error
        }
    }

    /**
     * Elimina un reporte operativo por su ID.
     *
     * @param id ID del reporte a eliminar.
     * @return ResponseEntity 204 NO_CONTENT si se elimina correctamente, o un error (404 NOT_FOUND, 500 INTERNAL_SERVER_ERROR).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporte(@PathVariable Integer id) {
        try {
            reporteOperativoService.eliminarReporte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ---------------------------------------------------
    // Endpoints para Métodos de Búsqueda Personalizados
    // ---------------------------------------------------

    /**
     * Busca reportes por ID de incidente.
     * @param idIncidente ID del incidente.
     * @return ResponseEntity con la lista de reportes (200 OK) o 204 NO_CONTENT.
     */
    @GetMapping("/by-incidente/{idIncidente}")
    public ResponseEntity<List<ReporteOperativo>> buscarReportesPorIdIncidente(@PathVariable Integer idIncidente) {
        List<ReporteOperativo> reportes = reporteOperativoService.buscarReportesPorIdIncidente(idIncidente);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    /**
     * Busca reportes por ID de bombero emisor.
     * @param bomberoEmisorId ID del bombero emisor.
     * @return ResponseEntity con la lista de reportes (200 OK) o 204 NO_CONTENT.
     */
    @GetMapping("/by-bombero-emisor/{bomberoEmisorId}")
    public ResponseEntity<List<ReporteOperativo>> buscarReportesPorBomberoEmisorId(@PathVariable Integer bomberoEmisorId) {
        List<ReporteOperativo> reportes = reporteOperativoService.buscarReportesPorBomberoEmisorId(bomberoEmisorId);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    /**
     * Busca reportes por estado de operación.
     * @param estadoOperacion Estado de la operación.
     * @return ResponseEntity con la lista de reportes (200 OK) o 204 NO_CONTENT.
     */
    @GetMapping("/by-estado/{estadoOperacion}")
    public ResponseEntity<List<ReporteOperativo>> buscarReportesPorEstadoOperacion(@PathVariable String estadoOperacion) {
        List<ReporteOperativo> reportes = reporteOperativoService.buscarReportesPorEstadoOperacion(estadoOperacion);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }

    /**
     * Busca reportes por tipo de operación (ignorando mayúsculas/minúsculas).
     * @param tipoOperacion Tipo de operación.
     * @return ResponseEntity con la lista de reportes (200 OK) o 204 NO_CONTENT.
     */
    @GetMapping("/by-tipo-operacion/{tipoOperacion}")
    public ResponseEntity<List<ReporteOperativo>> buscarReportesPorTipoOperacion(@PathVariable String tipoOperacion) {
        List<ReporteOperativo> reportes = reporteOperativoService.buscarReportesPorTipoOperacion(tipoOperacion);
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
}