package com.SAFE_Rescue.API_Comunicacion.service;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import com.SAFE_Rescue.API_Comunicacion.repository.BorradorMensajeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa la lógica de negocio para la gestión de Borradores de Mensajes.
 * Permite crear, actualizar, obtener y eliminar borradores.
 */
@Service
public class BorradorMensajeService {

    private final BorradorMensajeRepository borradorMensajeRepository;

    /**
     * Constructor para inyección de dependencias del repositorio de borradores.
     * @param borradorMensajeRepository El repositorio para interactuar con la base de datos.
     */
    @Autowired
    public BorradorMensajeService(BorradorMensajeRepository borradorMensajeRepository) {
        this.borradorMensajeRepository = borradorMensajeRepository;
    }

    /**
     * Guarda un nuevo borrador de mensaje.
     * Asigna la fecha actual si no está establecida y lo marca como no enviado.
     * @param borrador El objeto BorradorMensaje a guardar.
     * @return El borrador guardado.
     */
    public BorradorMensaje guardarBorrador(BorradorMensaje borrador) {
        if (borrador.getFechaBrdrMensaje() == null) {
            borrador.setFechaBrdrMensaje(new Date());
        }
        borrador.setBorradorEnviado(false); // Por defecto, un borrador nuevo no está enviado.
        return borradorMensajeRepository.save(borrador);
    }

    /**
     * Actualiza un borrador de mensaje existente.
     * Permite la actualización parcial de título y contenido.
     * @param idBorrador ID del borrador a actualizar.
     * @param datosActualizados Objeto con los campos a actualizar (título, contenido).
     * @return El borrador actualizado.
     * @throws RuntimeException Si el borrador no se encuentra.
     */
    @Transactional
    public BorradorMensaje actualizarBorrador(int idBorrador, BorradorMensaje datosActualizados) {
        Optional<BorradorMensaje> borradorExistente = borradorMensajeRepository.findById(idBorrador);

        if (borradorExistente.isPresent()) {
            BorradorMensaje borrador = borradorExistente.get();

            if (datosActualizados.getBrdrTitulo() != null) {
                borrador.setBrdrTitulo(datosActualizados.getBrdrTitulo());
            }
            if (datosActualizados.getBrdrContenido() != null) {
                borrador.setBrdrContenido(datosActualizados.getBrdrContenido());
            }

            return borradorMensajeRepository.save(borrador);
        } else {
            throw new RuntimeException("Borrador no encontrado con ID: " + idBorrador);
        }
    }

    /**
     * Obtiene una lista de todos los borradores de mensajes.
     * @return Una lista de objetos BorradorMensaje.
     */
    public List<BorradorMensaje> obtenerTodosLosBorradores() {
        return borradorMensajeRepository.findAll();
    }

    /**
     * Busca un borrador de mensaje por su identificador único.
     * @param id ID del borrador a buscar.
     * @return Un Optional que contiene el BorradorMensaje si es encontrado.
     */
    public Optional<BorradorMensaje> obtenerBorradorPorId(int id) {
        return borradorMensajeRepository.findById(id);
    }

    /**
     * Elimina un borrador de mensaje por su identificador único.
     * @param id ID del borrador a eliminar.
     * @throws RuntimeException Si el borrador no se encuentra.
     */
    @Transactional
    public void eliminarBorrador(int id) {
        if (borradorMensajeRepository.existsById(id)) {
            borradorMensajeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Borrador no encontrado con ID: " + id + " para eliminar.");
        }
    }
}