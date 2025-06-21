package com.SAFE_Rescue.API_Capacitaciones.controller;

import com.SAFE_Rescue.API_Capacitaciones.modelo.RecursoEducativo;
import com.SAFE_Rescue.API_Capacitaciones.service.RecursoEducativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date; // Necesario para Date en los DTOs y manejo de fechas
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de Recursos Educativos en la API de Capacitaciones.
 * Expone endpoints HTTP para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * y búsquedas personalizadas sobre los recursos educativos.
 */
@RestController // Indica que esta clase es un controlador REST y maneja solicitudes HTTP
@RequestMapping("api-capacitaciones/v1/recursos-educativos") // Define la ruta base para todos los endpoints de este controlador
public class RecursoEducativoController {

    private final RecursoEducativoService recursoEducativoService;

    /**
     * Constructor para inyección de dependencias del servicio de recursos educativos.
     * Spring inyecta automáticamente la instancia de {@link RecursoEducativoService}.
     *
     * @param recursoEducativoService El servicio que contiene la lógica de negocio para los recursos educativos.
     */
    @Autowired
    public RecursoEducativoController(RecursoEducativoService recursoEducativoService) {
        this.recursoEducativoService = recursoEducativoService;
    }

    // ---------------------------------------------------
    // Endpoints RESTful para Operaciones CRUD Básicas
    // ---------------------------------------------------

    /**
     * Maneja las solicitudes GET para obtener una lista de todos los recursos educativos.
     *
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK).
     * Si no hay recursos, devuelve una lista vacía.
     */
    @GetMapping
    public ResponseEntity<List<RecursoEducativo>> obtenerTodosLosRecursos() {
        List<RecursoEducativo> recursos = recursoEducativoService.obtenerTodosLosRecursos();
        return new ResponseEntity<>(recursos, HttpStatus.OK);
    }

    /**
     * Maneja las solicitudes GET para obtener un recurso educativo específico por su ID.
     *
     * @param id El ID (identificador único) del recurso educativo a buscar, tomado de la URL.
     * @return ResponseEntity con el {@link RecursoEducativo} y un estado HTTP 200 (OK) si se encuentra.
     * Si el recurso no se encuentra, devuelve un estado HTTP 404 (NOT_FOUND).
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecursoEducativo> obtenerRecursoPorId(@PathVariable Integer id) {
        Optional<RecursoEducativo> recurso = recursoEducativoService.obtenerRecursoPorId(id);
        return recurso.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo recurso educativo.
     * Recibe los datos del recurso a través de un DTO {@link CrearRecursoRequest} en el cuerpo de la solicitud.
     *
     * @param request El DTO {@link CrearRecursoRequest} que contiene los datos del nuevo recurso.
     * @return ResponseEntity con el {@link RecursoEducativo} creado y un estado HTTP 201 (CREATED) si es exitoso.
     * Retorna un estado HTTP 400 (BAD_REQUEST) si los datos de entrada no cumplen con las validaciones
     * definidas en el servicio.
     * Retorna un estado HTTP 500 (INTERNAL_SERVER_ERROR) para cualquier otra excepción inesperada.
     * @apiNote Ejemplo de cuerpo JSON para la petición POST:
     * <pre>{@code
     * {
     * "tipoRecursoEd": "Artículo Científico",
     * "nombre": "Estudio sobre Rescate en Espacios Confinados",
     * "descripcion": "Análisis de técnicas y equipos para rescates en espacios reducidos.",
     * "autor": "Dr. Sofia Morales",
     * "url": "https://journals.safereview.org/estudio-rescate-confinados.pdf",
     * "fechaPublicacionAutor": "2020-03-15T10:30:00.000+00:00" // Formato ISO 8601
     * }
     * }</pre>
     */
    @PostMapping
    public ResponseEntity<RecursoEducativo> crearRecurso(@RequestBody CrearRecursoRequest request) {
        try {
            RecursoEducativo nuevoRecurso = recursoEducativoService.crearRecursoDesdeRequest(request);
            return new ResponseEntity<>(nuevoRecurso, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Captura las excepciones de validación lanzadas por el servicio
            // Se devuelve 400 BAD_REQUEST y el cuerpo de la respuesta es null (según tu implementación actual)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            // Este catch está presente, pero la lógica del servicio actual no lo usa explícitamente para crear.
            // Podría usarse si hubiera validaciones de estado que llevaran a un conflicto.
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada del servidor
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Maneja las solicitudes PUT para actualizar un recurso educativo existente por su ID.
     * Recibe los datos actualizados a través de un DTO {@link ActualizarRecursoRequest} en el cuerpo de la solicitud.
     *
     * @param id El ID del recurso educativo a actualizar, tomado de la URL.
     * @param request El DTO {@link ActualizarRecursoRequest} con los datos a aplicar.
     * @return ResponseEntity con el {@link RecursoEducativo} actualizado y un estado HTTP 200 (OK) si es exitoso.
     * Retorna un estado HTTP 404 (NOT_FOUND) si el recurso con el ID dado no existe.
     * Retorna un estado HTTP 400 (BAD_REQUEST) si los datos de entrada no cumplen con las validaciones.
     * Retorna un estado HTTP 500 (INTERNAL_SERVER_ERROR) para cualquier otra excepción inesperada.
     * @apiNote Ejemplo de cuerpo JSON para la petición PUT:
     * <pre>{@code
     * {
     * "tipoRecursoEd": "Artículo Científico",
     * "nombre": "Estudio sobre Rescate en Espacios Confinados (Actualizado)",
     * "descripcion": "Nueva versión con datos de 2023.",
     * "autor": "Dr. Sofia Morales",
     * "url": "https://journals.safereview.org/estudio-rescate-confinados-v2.pdf",
     * "fechaPublicacionAutor": "2023-01-20T09:00:00.000+00:00" // Nueva fecha de publicación del autor
     * }
     * }</pre>
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecursoEducativo> actualizarRecurso(@PathVariable Integer id, @RequestBody ActualizarRecursoRequest request) {
        try {
            Optional<RecursoEducativo> recursoActualizado = recursoEducativoService.actualizarRecursoDesdeRequest(id, request);
            return recursoActualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no se encuentra, devuelve 404
        } catch (IllegalArgumentException e) {
            // Captura las excepciones de validación lanzadas por el servicio
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Devuelve 400 BAD_REQUEST
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Devuelve 500 INTERNAL_SERVER_ERROR
        }
    }

    /**
     * Maneja las solicitudes DELETE para eliminar un recurso educativo por su ID.
     *
     * @param id El ID del recurso educativo a eliminar, tomado de la URL.
     * @return ResponseEntity con un estado HTTP 204 (NO_CONTENT) si la eliminación es exitosa.
     * Retorna un estado HTTP 404 (NOT_FOUND) si el recurso con el ID dado no existe.
     * Retorna un estado HTTP 500 (INTERNAL_SERVER_ERROR) para cualquier otra excepción inesperada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Integer id) {
        try {
            recursoEducativoService.eliminarRecurso(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content para eliminación exitosa
        } catch (RuntimeException e) {
            // Captura la excepción lanzada por el servicio si el recurso no es encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 NOT_FOUND
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Devuelve 500 INTERNAL_SERVER_ERROR
        }
    }

    // ---------------------------------------------------
    // Endpoints para Métodos de Búsqueda Personalizados
    // ---------------------------------------------------

    /**
     * Busca recursos educativos por un tipo de recurso específico.
     *
     * @param tipo El tipo de recurso a buscar, tomado de la URL.
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK)
     * si se encuentran recursos. Si no se encuentran recursos, devuelve un estado HTTP 204 (NO_CONTENT).
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<RecursoEducativo>> buscarPorTipo(@PathVariable("tipo") String tipo) {
        List<RecursoEducativo> recursos = recursoEducativoService.buscarPorTipo(tipo);
        if (recursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si no hay resultados
        }
        return new ResponseEntity<>(recursos, HttpStatus.OK); // 200 OK con la lista de recursos
    }

    /**
     * Busca recursos educativos por el nombre exacto de su autor.
     *
     * @param autor El nombre completo del autor a buscar, tomado de la URL.
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK)
     * si se encuentran recursos. Si no se encuentran recursos, devuelve un estado HTTP 204 (NO_CONTENT).
     */
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<RecursoEducativo>> buscarPorAutor(@PathVariable("autor") String autor) {
        List<RecursoEducativo> recursos = recursoEducativoService.buscarPorAutor(autor);
        if (recursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recursos, HttpStatus.OK);
    }

    /**
     * Busca recursos educativos cuyo nombre contiene la cadena de texto especificada,
     * realizando una búsqueda insensible a mayúsculas y minúsculas.
     *
     * @param nombre La cadena de texto (palabra clave o frase) a buscar dentro del nombre de los recursos,
     * tomada de la URL.
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK)
     * si se encuentran recursos. Si no se encuentran recursos, devuelve un estado HTTP 204 (NO_CONTENT).
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<RecursoEducativo>> buscarPorNombre(@PathVariable("nombre") String nombre) {
        List<RecursoEducativo> recursos = recursoEducativoService.buscarPorNombre(nombre);
        if (recursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recursos, HttpStatus.OK);
    }

    /**
     * Busca recursos educativos cuya fecha de publicación por el autor sea posterior a la fecha dada.
     * La fecha se espera como un String en formato ISO 8601 (ej: "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
     * y se parsea a un objeto {@link Date}.
     *
     * @param fechaStr La fecha límite inferior en formato String, tomada de la URL.
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK)
     * si se encuentran recursos. Si no se encuentran, devuelve 204 (NO_CONTENT).
     * Retorna 400 (BAD_REQUEST) si el formato de la fecha en la URL es inválido.
     * @apiNote Ejemplo de URL: {@code /api-capacitaciones/v1/recursos-educativos/publicado-despues/2020-01-01T00:00:00.000+00:00}
     */
    @GetMapping("/publicado-despues/{fecha}")
    public ResponseEntity<List<RecursoEducativo>> buscarPorFechaPublicacionAutorDespuesDe(@PathVariable("fecha") String fechaStr) {
        try {
            // Parsea el String de fecha a un objeto Date usando java.time.OffsetDateTime para ISO 8601 con zona horaria
            Date fecha = Date.from(java.time.OffsetDateTime.parse(fechaStr).toInstant());
            List<RecursoEducativo> recursos = recursoEducativoService.buscarPorFechaPublicacionAutorDespuesDe(fecha);
            if (recursos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recursos, HttpStatus.OK);
        } catch (java.time.format.DateTimeParseException e) {
            // Captura errores de parsing de fecha y devuelve BAD_REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Busca recursos educativos cuya fecha de creación en nuestro sistema sea anterior a la fecha dada.
     * La fecha se espera como un String en formato ISO 8601 (ej: "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
     * y se parsea a un objeto {@link Date}.
     *
     * @param fechaStr La fecha límite superior en formato String, tomada de la URL.
     * @return ResponseEntity con una {@link List} de {@link RecursoEducativo} y un estado HTTP 200 (OK)
     * si se encuentran recursos. Si no se encuentran, devuelve 204 (NO_CONTENT).
     * Retorna 400 (BAD_REQUEST) si el formato de la fecha en la URL es inválido.
     * @apiNote Ejemplo de URL: {@code /api-capacitaciones/v1/recursos-educativos/creado-antes/2024-05-01T00:00:00.000+00:00}
     */
    @GetMapping("/creado-antes/{fecha}")
    public ResponseEntity<List<RecursoEducativo>> buscarPorFechaCreacionRecursoAntesDe(@PathVariable("fecha") String fechaStr) {
        try {
            Date fecha = Date.from(java.time.OffsetDateTime.parse(fechaStr).toInstant());
            List<RecursoEducativo> recursos = recursoEducativoService.buscarPorFechaCreacionRecursoAntesDe(fecha);
            if (recursos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recursos, HttpStatus.OK);
        } catch (java.time.format.DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // ---------------------------------------------------
    // Clases DTO internas para Peticiones (REQUESTS)
    // Estas clases se utilizan para estructurar los datos de entrada de las solicitudes HTTP
    // ---------------------------------------------------

    /**
     * DTO (Data Transfer Object) para la creación de un nuevo Recurso Educativo.
     * Contiene los campos que se esperan del cliente al enviar una solicitud POST para crear un recurso.
     */
    @Data // Genera automáticamente getters, setters, toString(), equals() y hashCode() para los campos
    @NoArgsConstructor // Genera un constructor sin argumentos (útil para la deserialización JSON)
    @AllArgsConstructor // Genera un constructor con todos los argumentos
    public static class CrearRecursoRequest {
        private String tipoRecursoEd;
        private String nombre;
        private String descripcion;
        private String autor;
        private String url;
        /**
         * Fecha de publicación del recurso por el autor original.
         * Se espera que esta fecha sea proporcionada por el cliente en formato ISO 8601
         * (ej: "2020-03-15T10:30:00.000+00:00").
         */
        private Date fechaPublicacionAutor;
    }

    /**
     * DTO (Data Transfer Object) para la actualización de un Recurso Educativo existente.
     * Contiene los campos que pueden ser modificados por el cliente al enviar una solicitud PUT.
     */
    @Data // Genera automáticamente getters, setters, toString(), equals() y hashCode() para los campos
    @NoArgsConstructor // Genera un constructor sin argumentos (útil para la deserialización JSON)
    @AllArgsConstructor // Genera un constructor con todos los argumentos
    public static class ActualizarRecursoRequest {
        private String tipoRecursoEd;
        private String nombre;
        private String descripcion;
        private String autor;
        private String url;
        /**
         * Fecha de publicación del recurso por el autor original.
         * Se espera que esta fecha sea proporcionada por el cliente en formato ISO 8601
         * (ej: "2023-01-20T09:00:00.000+00:00").
         */
        private Date fechaPublicacionAutor;
    }
}