package com.SAFE_Rescue.API_Capacitaciones.service;

import com.SAFE_Rescue.API_Capacitaciones.modelo.RecursoEducativo;
import com.SAFE_Rescue.API_Capacitaciones.repositorio.RecursoEducativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// Importaciones de DTOs desde el controlador
import static com.SAFE_Rescue.API_Capacitaciones.controller.RecursoEducativoController.CrearRecursoRequest;
import static com.SAFE_Rescue.API_Capacitaciones.controller.RecursoEducativoController.ActualizarRecursoRequest;


/**
 * Servicio que implementa la lógica de negocio para la gestión de Recursos Educativos.
 * Actúa como intermediario entre el controlador y el repositorio, manejando las validaciones
 * y las reglas de negocio antes de interactuar con la capa de persistencia.
 */
@Service // Marca esta clase como un componente de servicio de Spring para el descubrimiento automático
public class RecursoEducativoService {

    private final RecursoEducativoRepository recursoEducativoRepository;

    /**
     * Constructor para inyección de dependencias del repositorio de recursos educativos.
     * Spring inyecta automáticamente una instancia de {@link RecursoEducativoRepository}.
     *
     * @param recursoEducativoRepository El repositorio para interactuar con la base de datos de recursos.
     */
    @Autowired
    public RecursoEducativoService(RecursoEducativoRepository recursoEducativoRepository) {
        this.recursoEducativoRepository = recursoEducativoRepository;
    }

    // ---------------------------------------------------
    // Métodos CRUD Básicos
    // ---------------------------------------------------

    /**
     * Recupera y devuelve una lista de todos los recursos educativos disponibles en el sistema.
     *
     * @return Una {@link List} de todos los {@link RecursoEducativo} almacenados.
     */
    public List<RecursoEducativo> obtenerTodosLosRecursos() {
        return recursoEducativoRepository.findAll();
    }

    /**
     * Busca y recupera un recurso educativo específico por su identificador único.
     *
     * @param id El ID (identificador único) del recurso educativo a buscar.
     * @return Un {@link Optional} que contiene el {@link RecursoEducativo} si se encuentra,
     * o {@link Optional#empty()} si no existe un recurso con el ID proporcionado.
     */
    public Optional<RecursoEducativo> obtenerRecursoPorId(Integer id) {
        return recursoEducativoRepository.findById(id);
    }

    /**
     * Crea un nuevo recurso educativo en el sistema a partir de los datos proporcionados en un DTO de solicitud.
     * Este método realiza validaciones manuales sobre los campos esenciales del recurso.
     * La fecha de publicación del autor se toma directamente del DTO de entrada, mientras que
     * la {@code fechaCreacionRecurso} (fecha de registro en nuestro sistema) se asigna automáticamente al momento de la creación.
     *
     * @param request El DTO {@link CrearRecursoRequest} que contiene los datos para el nuevo recurso.
     * @return La entidad {@link RecursoEducativo} guardada en la base de datos, con su ID y fechas asignadas.
     * @throws IllegalArgumentException Si alguno de los campos del {@code request} es nulo, está en blanco,
     * fuera de rango de longitud, o no cumple con el formato esperado (ej. URL inválida).
     */
    public RecursoEducativo crearRecursoDesdeRequest(CrearRecursoRequest request) {
        // --- Validaciones de campos de entrada (replicando lógica de @Valid de Jakarta Validation) ---
        if (request.getTipoRecursoEd() == null || request.getTipoRecursoEd().isBlank() || request.getTipoRecursoEd().length() < 2 || request.getTipoRecursoEd().length() > 50) {
            throw new IllegalArgumentException("El tipo de recurso debe tener entre 2 y 50 caracteres y no puede estar en blanco.");
        }
        if (request.getNombre() == null || request.getNombre().isBlank() || request.getNombre().length() < 3 || request.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre del recurso debe tener entre 3 y 100 caracteres y no puede estar en blanco.");
        }
        if (request.getAutor() == null || request.getAutor().isBlank() || request.getAutor().length() < 3 || request.getAutor().length() > 100) {
            throw new IllegalArgumentException("El autor debe tener entre 3 y 100 caracteres y no puede estar en blanco.");
        }
        if (request.getUrl() == null || request.getUrl().isBlank() || request.getUrl().length() > 500) {
            throw new IllegalArgumentException("La URL no puede estar en blanco y no puede exceder los 500 caracteres.");
        }
        // Validación de formato de URL (expresión regular básica para http/https/ftp/file)
        if (!request.getUrl().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
            throw new IllegalArgumentException("La URL proporcionada no es válida.");
        }
        if (request.getDescripcion() != null && request.getDescripcion().length() > 1000) {
            throw new IllegalArgumentException("La descripción no puede exceder los 1000 caracteres.");
        }
        // La fecha de publicación del autor es un campo obligatorio
        if (request.getFechaPublicacionAutor() == null) {
            throw new IllegalArgumentException("La fecha de publicación del autor no puede ser nula.");
        }
        // --- Fin de validaciones ---

        // Mapea los datos del DTO a la entidad de la base de datos
        RecursoEducativo nuevoRecurso = new RecursoEducativo();
        nuevoRecurso.setTipoRecursoEd(request.getTipoRecursoEd());
        nuevoRecurso.setNombre(request.getNombre());
        nuevoRecurso.setDescripcion(request.getDescripcion());
        nuevoRecurso.setAutor(request.getAutor());
        nuevoRecurso.setUrl(request.getUrl());
        nuevoRecurso.setFechaPublicacionAutor(request.getFechaPublicacionAutor());

        // Asigna automáticamente la fecha de creación del registro en nuestro sistema
        nuevoRecurso.setFechaCreacionRecurso(new Date());

        return recursoEducativoRepository.save(nuevoRecurso);
    }

    /**
     * Actualiza un recurso educativo existente en la base de datos a partir de un DTO de solicitud.
     * Este método busca el recurso por su ID y aplica las actualizaciones si se encuentra,
     * realizando validaciones manuales sobre los campos proporcionados en el DTO de actualización.
     * La {@code fechaCreacionRecurso} del recurso NO se actualiza, ya que representa la fecha original de registro.
     *
     * @param id El ID del recurso educativo a actualizar.
     * @param request El DTO {@link ActualizarRecursoRequest} con los datos que se desean actualizar.
     * @return Un {@link Optional} que contiene la entidad {@link RecursoEducativo} actualizada si el recurso
     * se encontró y se actualizó exitosamente, o {@link Optional#empty()} si el recurso con el ID dado no existe.
     * @throws IllegalArgumentException Si alguno de los campos del {@code request} es nulo, está en blanco,
     * fuera de rango de longitud, o no cumple con el formato esperado (ej. URL inválida).
     */
    public Optional<RecursoEducativo> actualizarRecursoDesdeRequest(Integer id, ActualizarRecursoRequest request) {
        // Busca el recurso existente por su ID
        return recursoEducativoRepository.findById(id).map(recursoExistente -> {
            // --- Validaciones de campos de entrada para actualización ---
            // Se asume que todos los campos del DTO de actualización son obligatorios si se envían.
            // Si un campo es null en el request, se puede optar por no actualizarlo o por invalidarlo.
            // Aquí se invalidan si son null/blank/fuera de rango.
            if (request.getTipoRecursoEd() == null || request.getTipoRecursoEd().isBlank() || request.getTipoRecursoEd().length() < 2 || request.getTipoRecursoEd().length() > 50) {
                throw new IllegalArgumentException("El tipo de recurso debe tener entre 2 y 50 caracteres y no puede estar en blanco.");
            }
            if (request.getNombre() == null || request.getNombre().isBlank() || request.getNombre().length() < 3 || request.getNombre().length() > 100) {
                throw new IllegalArgumentException("El nombre del recurso debe tener entre 3 y 100 caracteres y no puede estar en blanco.");
            }
            if (request.getAutor() == null || request.getAutor().isBlank() || request.getAutor().length() < 3 || request.getAutor().length() > 100) {
                throw new IllegalArgumentException("El autor debe tener entre 3 y 100 caracteres y no puede estar en blanco.");
            }
            if (request.getUrl() == null || request.getUrl().isBlank() || request.getUrl().length() > 500) {
                throw new IllegalArgumentException("La URL no puede estar en blanco y no puede exceder los 500 caracteres.");
            }
            if (!request.getUrl().matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
                throw new IllegalArgumentException("La URL proporcionada no es válida.");
            }
            if (request.getDescripcion() != null && request.getDescripcion().length() > 1000) {
                throw new IllegalArgumentException("La descripción no puede exceder los 1000 caracteres.");
            }
            // La fecha de publicación del autor también es obligatoria en la actualización
            if (request.getFechaPublicacionAutor() == null) {
                throw new IllegalArgumentException("La fecha de publicación del autor no puede ser nula.");
            }
            // --- Fin de validaciones ---

            // Actualiza los campos del recurso existente con los datos del DTO
            recursoExistente.setTipoRecursoEd(request.getTipoRecursoEd());
            recursoExistente.setNombre(request.getNombre());
            recursoExistente.setDescripcion(request.getDescripcion());
            recursoExistente.setAutor(request.getAutor());
            recursoExistente.setUrl(request.getUrl());
            recursoExistente.setFechaPublicacionAutor(request.getFechaPublicacionAutor());

            // La fechaCreacionRecurso NO se actualiza aquí, ya que representa la fecha original de registro
            // recursoExistente.setFechaCreacionRecurso(request.getFechaCreacionRecurso()); // NO HACER ESTO

            return recursoEducativoRepository.save(recursoExistente); // Guarda el recurso actualizado
        });
    }

    /**
     * Elimina un recurso educativo de la base de datos por su identificador único.
     * Antes de eliminar, verifica si el recurso existe para evitar errores.
     *
     * @param id El ID del recurso educativo a eliminar.
     * @throws RuntimeException Si el recurso con el ID especificado no se encuentra en la base de datos,
     * indicando que no hay nada que eliminar.
     */
    public void eliminarRecurso(Integer id) {
        // Verifica si el recurso existe antes de intentar eliminarlo
        if (!recursoEducativoRepository.existsById(id)) {
            throw new RuntimeException("Recurso educativo con ID " + id + " no encontrado para eliminar.");
        }
        recursoEducativoRepository.deleteById(id); // Elimina el recurso
    }

    // ---------------------------------------------------
    // Métodos de Lógica de Negocio/Búsqueda Personalizada
    // ---------------------------------------------------

    /**
     * Busca y recupera una lista de recursos educativos que pertenecen a un tipo específico.
     * La búsqueda es sensible a mayúsculas y minúsculas y requiere una coincidencia exacta del tipo.
     *
     * @param tipoRecursoEd El tipo de recurso a buscar (ej: "Video", "Manual").
     * @return Una {@link List} de {@link RecursoEducativo} que coinciden con el tipo proporcionado.
     */
    public List<RecursoEducativo> buscarPorTipo(String tipoRecursoEd) {
        return recursoEducativoRepository.findByTipoRecursoEd(tipoRecursoEd);
    }

    /**
     * Busca y recupera una lista de recursos educativos por el nombre exacto de su autor.
     * La búsqueda es sensible a mayúsculas y minúsculas y requiere una coincidencia exacta del autor.
     *
     * @param autor El nombre completo del autor a buscar.
     * @return Una {@link List} de {@link RecursoEducativo} que han sido creados por el autor especificado.
     */
    public List<RecursoEducativo> buscarPorAutor(String autor) {
        return recursoEducativoRepository.findByAutor(autor);
    }

    /**
     * Busca y recupera una lista de recursos educativos cuyo nombre contiene la cadena de texto especificada.
     * La búsqueda es insensible a mayúsculas y minúsculas, lo que permite coincidencias parciales.
     *
     * @param nombre La cadena de texto (palabra clave o frase) a buscar dentro del nombre de los recursos.
     * @return Una {@link List} de {@link RecursoEducativo} que tienen el nombre que contiene la cadena buscada.
     */
    public List<RecursoEducativo> buscarPorNombre(String nombre) {
        return recursoEducativoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca y recupera una lista de recursos educativos cuya fecha de publicación por el autor
     * es posterior a la fecha límite proporcionada.
     *
     * @param fecha La {@link Date} que sirve como el límite inferior (exclusivo) para la fecha de publicación del autor.
     * @return Una {@link List} de {@link RecursoEducativo} publicados por el autor después de la fecha dada.
     */
    public List<RecursoEducativo> buscarPorFechaPublicacionAutorDespuesDe(Date fecha) {
        return recursoEducativoRepository.findByFechaPublicacionAutorAfter(fecha);
    }

    /**
     * Busca y recupera una lista de recursos educativos cuya fecha de creación en nuestro sistema
     * es anterior a la fecha límite proporcionada.
     *
     * @param fecha La {@link Date} que sirve como el límite superior (exclusivo) para la fecha de creación en el sistema.
     * @return Una {@link List} de {@link RecursoEducativo} que fueron registrados en el sistema antes de la fecha dada.
     */
    public List<RecursoEducativo> buscarPorFechaCreacionRecursoAntesDe(Date fecha) {
        return recursoEducativoRepository.findByFechaCreacionRecursoBefore(fecha);
    }
}