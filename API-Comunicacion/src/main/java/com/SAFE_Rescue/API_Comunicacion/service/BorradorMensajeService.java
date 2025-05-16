package com.SAFE_Rescue.API_Comunicacion.service;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import com.SAFE_Rescue.API_Comunicacion.repository.BorradorMensajeRepository;
import jakarta.transaction.Transactional; // Usamos jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Para manejar el caso de que un borrador no exista
import java.util.Date; // Para la fecha

@Service // Indica a Spring que esta clase es un componente de Servicio
public class BorradorMensajeService {

    // Inyectamos la interfaz del Repositorio para interactuar con la base de datos
    private final BorradorMensajeRepository borradorMensajeRepository;

    @Autowired // Spring inyectará la dependencia automáticamente
    public BorradorMensajeService(BorradorMensajeRepository borradorMensajeRepository) {
        this.borradorMensajeRepository = borradorMensajeRepository;
    }

    /**
     * Guarda un nuevo borrador o actualiza uno existente.
     * Si el borrador tiene un ID, intenta actualizar; de lo contrario, crea uno nuevo.
     * @param borrador El objeto BorradorMensaje a guardar o actualizar.
     * @return El BorradorMensaje guardado/actualizado.
     */
    @Transactional // Gestiona la transacción de la base de datos
    public BorradorMensaje guardarBorrador(BorradorMensaje borrador) {
        // Aquí podrías añadir validaciones de negocio antes de guardar
        // Por ejemplo, validar que el emisor exista, que los campos obligatorios no estén vacíos, etc.
        // if (borrador.getBrdr_emisor() == 0) {
        //     throw new IllegalArgumentException("El emisor del borrador no puede ser cero.");
        // }
        if (borrador.getFecha_brdr_mensaje() == null) {
            borrador.setFecha_brdr_mensaje(new Date()); // Asignar fecha actual si no tiene
        }
        return borradorMensajeRepository.save(borrador);
    }

    /**
     * Actualiza un borrador existente por su ID.
     * Solo actualiza los campos permitidos (titulo, contenido).
     * @param idBorrador El ID del borrador a actualizar.
     * @param datosActualizados Un BorradorMensaje con los datos para actualizar.
     * @return El BorradorMensaje actualizado.
     * @throws RuntimeException si el borrador no se encuentra.
     */
    @Transactional
    public BorradorMensaje actualizarBorrador(int idBorrador, BorradorMensaje datosActualizados) {

        Optional<BorradorMensaje> borradorExistente = borradorMensajeRepository.findById(idBorrador);



        if (borradorExistente.isPresent()) {
            BorradorMensaje borrador = borradorExistente.get();


            if (datosActualizados.getBrdr_titulo() != null) {
                borrador.setBrdr_titulo(datosActualizados.getBrdr_titulo());
            }
            if (datosActualizados.getBrdr_contenido() != null) {
                borrador.setBrdr_contenido(datosActualizados.getBrdr_contenido());
            }

            return borradorMensajeRepository.save(borrador);
        } else {
            throw new RuntimeException("Borrador no encontrado con ID: " + idBorrador);
        }
    }

}