package com.SAFE_Rescue.API_Notificaciones.repository;

import com.SAFE_Rescue.API_Notificaciones.modelo.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para la entidad {@link Notificacion}.
 * Proporciona métodos CRUD y funcionalidades de paginación y ordenación
 * heredadas de {@link JpaRepository}.
 * Permite la interacción con la tabla `notificacion_emergencia` en la base de datos.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}