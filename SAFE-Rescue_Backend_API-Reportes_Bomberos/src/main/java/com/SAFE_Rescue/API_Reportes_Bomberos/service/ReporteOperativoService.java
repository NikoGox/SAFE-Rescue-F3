package com.SAFE_Rescue.API_Reportes_Bomberos.service;

import com.SAFE_Rescue.API_Reportes_Bomberos.modelo.ReporteOperativo;
import com.SAFE_Rescue.API_Reportes_Bomberos.repository.ReporteOperativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa la lógica de negocio para la gestión de Reportes Operativos.
 * Proporciona métodos para operaciones CRUD y búsquedas.
 */
@Service
public class ReporteOperativoService {

    private final ReporteOperativoRepository reporteOperativoRepository;

    /**
     * Constructor para inyección de dependencias del repositorio de reportes operativos.
     * @param reporteOperativoRepository El repositorio para interactuar con la base de datos.
     */
    @Autowired
    public ReporteOperativoService(ReporteOperativoRepository reporteOperativoRepository) {
        this.reporteOperativoRepository = reporteOperativoRepository;
    }

    /**
     * Crea un nuevo reporte operativo, aplicando validaciones manuales.
     *
     * @param reporte El reporte a guardar.
     * @return El reporte creado.
     * @throws IllegalArgumentException Si algún campo no cumple las validaciones.
     */
    @Transactional
    public ReporteOperativo crearReporte(ReporteOperativo reporte) {
        // --- Validaciones de campos de entrada para creación ---
        if (reporte.getIdIncidente() == null || reporte.getIdIncidente() <= 0) {
            throw new IllegalArgumentException("El ID del incidente es obligatorio y debe ser un número positivo.");
        }
        if (reporte.getBomberoEmisorId() == null || reporte.getBomberoEmisorId() <= 0) {
            throw new IllegalArgumentException("El ID del bombero emisor es obligatorio y debe ser un número positivo.");
        }
        if (reporte.getTipoOperacion() == null || reporte.getTipoOperacion().isBlank()) {
            throw new IllegalArgumentException("El tipo de operación es obligatorio.");
        }
        if (reporte.getTipoOperacion().length() > 100) {
            throw new IllegalArgumentException("El tipo de operación no debe exceder los 100 caracteres.");
        }
        if (reporte.getEstadoOperacion() == null || reporte.getEstadoOperacion().isBlank()) {
            throw new IllegalArgumentException("El estado de la operación es obligatorio.");
        }
        if (reporte.getEstadoOperacion().length() > 50) {
            throw new IllegalArgumentException("El estado de la operación no debe exceder los 50 caracteres.");
        }

        if (reporte.getDuracionOperacionMinutos() != null && reporte.getDuracionOperacionMinutos() < 0) {
            throw new IllegalArgumentException("La duración de la operación no puede ser negativa.");
        }
        if (reporte.getNumHeridos() != null && reporte.getNumHeridos() < 0) {
            throw new IllegalArgumentException("El número de heridos no puede ser negativo.");
        }
        if (reporte.getNumFallecidos() != null && reporte.getNumFallecidos() < 0) {
            throw new IllegalArgumentException("El número de fallecidos no puede ser negativo.");
        }
        if (reporte.getNumEvacuados() != null && reporte.getNumEvacuados() < 0) {
            throw new IllegalArgumentException("El número de evacuados no puede ser negativo.");
        }
        if (reporte.getNumDesaparecidos() != null && reporte.getNumDesaparecidos() < 0) {
            throw new IllegalArgumentException("El número de desaparecidos no puede ser negativo.");
        }
        if (reporte.getRecursosUtilizadosDetalle() != null && reporte.getRecursosUtilizadosDetalle().length() > 1000) {
            throw new IllegalArgumentException("El detalle de recursos utilizados no puede exceder los 1000 caracteres.");
        }
        if (reporte.getEquipoParticipante() != null && reporte.getEquipoParticipante().length() > 1000) {
            throw new IllegalArgumentException("El detalle del equipo participante no puede exceder los 1000 caracteres.");
        }
        if (reporte.getCausaProbable() != null && reporte.getCausaProbable().length() > 255) {
            throw new IllegalArgumentException("La causa probable no puede exceder los 255 caracteres.");
        }
        if (reporte.getObservacionesAdicionales() != null && reporte.getObservacionesAdicionales().length() > 2000) {
            throw new IllegalArgumentException("Las observaciones adicionales no pueden exceder los 2000 caracteres.");
        }

        reporte.setFechaHoraReporte(new Date());
        return reporteOperativoRepository.save(reporte);
    }

    /**
     * Obtiene una lista de todos los reportes operativos.
     *
     * @return Lista de reportes.
     */
    public List<ReporteOperativo> obtenerTodosLosReportes() {
        return reporteOperativoRepository.findAll();
    }

    /**
     * Obtiene un reporte operativo por su ID.
     *
     * @param id ID del reporte.
     * @return Optional del reporte si existe.
     */
    public Optional<ReporteOperativo> obtenerReportePorId(Integer id) {
        return reporteOperativoRepository.findById(id);
    }

    /**
     * Actualiza un reporte operativo existente, aplicando validaciones manuales.
     *
     * @param id ID del reporte a actualizar.
     * @param datosActualizados Objeto con los datos para la actualización.
     * @return Optional del reporte actualizado si existe.
     * @throws IllegalArgumentException Si algún campo no cumple las validaciones.
     */
    @Transactional
    public Optional<ReporteOperativo> actualizarReporte(Integer id, ReporteOperativo datosActualizados) {
        return reporteOperativoRepository.findById(id).map(reporteExistente -> {
            if (datosActualizados.getIdIncidente() != null) {
                if (datosActualizados.getIdIncidente() <= 0) {
                    throw new IllegalArgumentException("El ID del incidente debe ser un número positivo.");
                }
                reporteExistente.setIdIncidente(datosActualizados.getIdIncidente());
            }

            if (datosActualizados.getTipoOperacion() != null) {
                if (datosActualizados.getTipoOperacion().isBlank()) {
                    throw new IllegalArgumentException("El tipo de operación no puede estar vacío.");
                }
                if (datosActualizados.getTipoOperacion().length() > 100) {
                    throw new IllegalArgumentException("El tipo de operación no debe exceder los 100 caracteres.");
                }
                reporteExistente.setTipoOperacion(datosActualizados.getTipoOperacion());
            }

            if (datosActualizados.getEstadoOperacion() != null) {
                if (datosActualizados.getEstadoOperacion().isBlank()) {
                    throw new IllegalArgumentException("El estado de la operación no puede estar vacío.");
                }
                if (datosActualizados.getEstadoOperacion().length() > 50) {
                    throw new IllegalArgumentException("El estado de la operación no debe exceder los 50 caracteres.");
                }
                reporteExistente.setEstadoOperacion(datosActualizados.getEstadoOperacion());
            }

            if (datosActualizados.getDuracionOperacionMinutos() != null) {
                if (datosActualizados.getDuracionOperacionMinutos() < 0) {
                    throw new IllegalArgumentException("La duración de la operación no puede ser negativa.");
                }
                reporteExistente.setDuracionOperacionMinutos(datosActualizados.getDuracionOperacionMinutos());
            }
            if (datosActualizados.getNumHeridos() != null) {
                if (datosActualizados.getNumHeridos() < 0) {
                    throw new IllegalArgumentException("El número de heridos no puede ser negativo.");
                }
                reporteExistente.setNumHeridos(datosActualizados.getNumHeridos());
            }
            if (datosActualizados.getNumFallecidos() != null) {
                if (datosActualizados.getNumFallecidos() < 0) {
                    throw new IllegalArgumentException("El número de fallecidos no puede ser negativo.");
                }
                reporteExistente.setNumFallecidos(datosActualizados.getNumFallecidos());
            }
            if (datosActualizados.getNumEvacuados() != null) {
                if (datosActualizados.getNumEvacuados() < 0) {
                    throw new IllegalArgumentException("El número de evacuados no puede ser negativo.");
                }
                reporteExistente.setNumEvacuados(datosActualizados.getNumEvacuados());
            }
            if (datosActualizados.getNumDesaparecidos() != null) {
                if (datosActualizados.getNumDesaparecidos() < 0) {
                    throw new IllegalArgumentException("El número de desaparecidos no puede ser negativo.");
                }
                reporteExistente.setNumDesaparecidos(datosActualizados.getNumDesaparecidos());
            }
            if (datosActualizados.getRecursosUtilizadosDetalle() != null) {
                if (datosActualizados.getRecursosUtilizadosDetalle().length() > 1000) {
                    throw new IllegalArgumentException("El detalle de recursos utilizados no puede exceder los 1000 caracteres.");
                }
                reporteExistente.setRecursosUtilizadosDetalle(datosActualizados.getRecursosUtilizadosDetalle());
            }
            if (datosActualizados.getEquipoParticipante() != null) {
                if (datosActualizados.getEquipoParticipante().length() > 1000) {
                    throw new IllegalArgumentException("El detalle del equipo participante no puede exceder los 1000 caracteres.");
                }
                reporteExistente.setEquipoParticipante(datosActualizados.getEquipoParticipante());
            }
            if (datosActualizados.getCausaProbable() != null) {
                if (datosActualizados.getCausaProbable().length() > 255) {
                    throw new IllegalArgumentException("La causa probable no puede exceder los 255 caracteres.");
                }
                reporteExistente.setCausaProbable(datosActualizados.getCausaProbable());
            }
            if (datosActualizados.getObservacionesAdicionales() != null) {
                if (datosActualizados.getObservacionesAdicionales().length() > 2000) {
                    throw new IllegalArgumentException("Las observaciones adicionales no pueden exceder los 2000 caracteres.");
                }
                reporteExistente.setObservacionesAdicionales(datosActualizados.getObservacionesAdicionales());
            }
            if (datosActualizados.getBomberoEmisorId() != null) {
                if (datosActualizados.getBomberoEmisorId() <= 0) {
                    throw new IllegalArgumentException("El ID del bombero emisor es obligatorio y debe ser un número positivo.");
                }
                reporteExistente.setBomberoEmisorId(datosActualizados.getBomberoEmisorId());
            }

            return reporteOperativoRepository.save(reporteExistente);
        });
    }

    /**
     * Elimina un reporte operativo por su ID.
     *
     * @param id ID del reporte a eliminar.
     * @throws RuntimeException Si el reporte no es encontrado.
     */
    public void eliminarReporte(Integer id) {
        if (!reporteOperativoRepository.existsById(id)) {
            throw new RuntimeException("Reporte Operativo con ID " + id + " no encontrado para eliminar.");
        }
        reporteOperativoRepository.deleteById(id);
    }

    /**
     * Busca reportes por ID de incidente.
     * @param idIncidente ID del incidente.
     * @return Lista de reportes encontrados.
     */
    public List<ReporteOperativo> buscarReportesPorIdIncidente(Integer idIncidente) {
        return reporteOperativoRepository.findByIdIncidente(idIncidente);
    }

    /**
     * Busca reportes por ID de bombero emisor.
     * @param bomberoEmisorId ID del bombero emisor.
     * @return Lista de reportes encontrados.
     */
    public List<ReporteOperativo> buscarReportesPorBomberoEmisorId(Integer bomberoEmisorId) {
        return reporteOperativoRepository.findByBomberoEmisorId(bomberoEmisorId);
    }

    /**
     * Busca reportes por estado de operación.
     * @param estadoOperacion Estado de la operación.
     * @return Lista de reportes encontrados.
     */
    public List<ReporteOperativo> buscarReportesPorEstadoOperacion(String estadoOperacion) {
        return reporteOperativoRepository.findByEstadoOperacion(estadoOperacion);
    }

    /**
     * Busca reportes por tipo de operación (ignorando mayúsculas/minúsculas).
     * @param tipoOperacion Tipo de operación.
     * @return Lista de reportes encontrados.
     */
    public List<ReporteOperativo> buscarReportesPorTipoOperacion(String tipoOperacion) {
        return reporteOperativoRepository.findByTipoOperacionContainingIgnoreCase(tipoOperacion);
    }
}