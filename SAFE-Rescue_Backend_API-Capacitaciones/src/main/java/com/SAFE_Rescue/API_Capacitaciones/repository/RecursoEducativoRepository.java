package com.SAFE_Rescue.API_Capacitaciones.repositorio;

import com.SAFE_Rescue.API_Capacitaciones.modelo.RecursoEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

/**
 * Repositorio para la entidad {@link RecursoEducativo}.
 * Proporciona métodos CRUD (Crear, Leer, Actualizar, Eliminar)
 * y define consultas personalizadas para la interacción con la base de datos.
 */
@Repository // Marca esta interfaz como un componente de repositorio de Spring para el descubrimiento automático
public interface RecursoEducativoRepository extends JpaRepository<RecursoEducativo, Integer> {

    /**
     * Busca y recupera una lista de recursos educativos por su tipo específico.
     *
     * @param tipoRecursoEd El tipo de recurso a buscar (ej: "Artículo", "Video", "Manual").
     * @return Una {@link List} de {@link RecursoEducativo} que coinciden con el tipo especificado.
     */
    List<RecursoEducativo> findByTipoRecursoEd(String tipoRecursoEd);

    /**
     * Busca y recupera una lista de recursos educativos por el nombre exacto de su autor.
     *
     * @param autor El nombre completo del autor del recurso.
     * @return Una {@link List} de {@link RecursoEducativo} creados por el autor especificado.
     */
    List<RecursoEducativo> findByAutor(String autor);

    /**
     * Busca y recupera una lista de recursos educativos cuyo nombre contiene la cadena de texto especificada,
     * realizando una búsqueda insensible a mayúsculas y minúsculas.
     *
     * @param nombre La cadena de texto a buscar dentro del nombre de los recursos.
     * @return Una {@link List} de {@link RecursoEducativo} que coinciden parcialmente con el nombre.
     */
    List<RecursoEducativo> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca y recupera una lista de recursos educativos cuya descripción contiene la cadena de texto especificada,
     * realizando una búsqueda insensible a mayúsculas y minúsculas.
     *
     * @param descripcion La cadena de texto a buscar dentro de la descripción de los recursos.
     * @return Una {@link List} de {@link RecursoEducativo} que coinciden parcialmente con la descripción.
     */
    List<RecursoEducativo> findByDescripcionContainingIgnoreCase(String descripcion);

    /**
     * Busca y recupera una lista de recursos educativos cuya {@code fechaPublicacionAutor}
     * es posterior a la fecha proporcionada.
     *
     * @param fecha La fecha a partir de la cual se buscarán los recursos.
     * @return Una {@link List} de {@link RecursoEducativo} publicados por el autor después de la fecha especificada.
     */
    List<RecursoEducativo> findByFechaPublicacionAutorAfter(Date fecha);

    /**
     * Busca y recupera una lista de recursos educativos cuya {@code fechaCreacionRecurso}
     * en nuestro sistema es anterior a la fecha proporcionada.
     *
     * @param fecha La fecha límite superior para la fecha de creación en el sistema.
     * @return Una {@link List} de {@link RecursoEducativo} creados en el sistema antes de la fecha especificada.
     */
    List<RecursoEducativo> findByFechaCreacionRecursoBefore(Date fecha);

    /**
     * Busca y recupera una lista de recursos educativos que coinciden tanto con el tipo de recurso
     * como con el nombre del autor proporcionados.
     *
     * @param tipoRecursoEd El tipo de recurso a buscar.
     * @param autor El nombre del autor a buscar.
     * @return Una {@link List} de {@link RecursoEducativo} que cumplen ambos criterios.
     */
    List<RecursoEducativo> findByTipoRecursoEdAndAutor(String tipoRecursoEd, String autor);

    // Se pueden añadir otros métodos de búsqueda por fecha si fueran necesarios, como Between, Before, etc.
}