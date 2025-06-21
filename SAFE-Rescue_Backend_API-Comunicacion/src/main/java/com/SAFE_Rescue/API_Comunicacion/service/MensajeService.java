package com.SAFE_Rescue.API_Comunicacion.service;

import com.SAFE_Rescue.API_Comunicacion.modelo.BorradorMensaje;
import com.SAFE_Rescue.API_Comunicacion.modelo.Mensaje;
import com.SAFE_Rescue.API_Comunicacion.repository.BorradorMensajeRepository;
import com.SAFE_Rescue.API_Comunicacion.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa la lógica de negocio para la gestión de Mensajes.
 * Permite crear, obtener y eliminar mensajes, y gestionar la relación con Borradores.
 */
@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final BorradorMensajeRepository borradorMensajeRepository;

    /**
     * Constructor para inyección de dependencias de los repositorios.
     * @param mensajeRepository Repositorio para la entidad Mensaje.
     * @param borradorMensajeRepository Repositorio para la entidad BorradorMensaje.
     */
    @Autowired
    public MensajeService(MensajeRepository mensajeRepository, BorradorMensajeRepository borradorMensajeRepository) {
        this.mensajeRepository = mensajeRepository;
        this.borradorMensajeRepository = borradorMensajeRepository;
    }

    /**
     * Crea un nuevo mensaje a partir de un borrador existente y lo asocia a un receptor.
     * Marca el borrador como enviado.
     * @param idBorrador ID del borrador original.
     * @param idReceptor ID del usuario/entidad que recibirá el mensaje.
     * @return El mensaje creado y guardado.
     * @throws RuntimeException Si el borrador no es encontrado.
     */
    @Transactional
    public Mensaje crearMensajeDesdeBorradorYReceptor(int idBorrador, int idReceptor) {
        Optional<BorradorMensaje> borradorOptional = borradorMensajeRepository.findById(idBorrador);

        if (borradorOptional.isEmpty()) {
            throw new RuntimeException("Borrador con ID " + idBorrador + " no encontrado para crear el mensaje.");
        }

        BorradorMensaje borrador = borradorOptional.get();

        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setIdEmisor(borrador.getIdBrdrEmisor());
        nuevoMensaje.setIdReceptor(idReceptor);
        nuevoMensaje.setFechaMensaje(new Date());
        nuevoMensaje.setTitulo(borrador.getBrdrTitulo());
        nuevoMensaje.setContenido(borrador.getBrdrContenido());
        nuevoMensaje.setBorradorOriginal(borrador);

        Mensaje mensajeGuardado = mensajeRepository.save(nuevoMensaje);

        // Marca el borrador como enviado
        borrador.setBorradorEnviado(true);
        borradorMensajeRepository.save(borrador);

        return mensajeGuardado;
    }

    /**
     * Obtiene una lista de todos los mensajes en el sistema.
     * @return Una lista de objetos Mensaje.
     */
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    /**
     * Busca un mensaje por su identificador único.
     * @param idMensaje ID del mensaje a buscar.
     * @return Un Optional que contiene el Mensaje si es encontrado.
     */
    public Optional<Mensaje> obtenerMensajePorId(int idMensaje) {
        return mensajeRepository.findById(idMensaje);
    }

    /**
     * Elimina un mensaje por su identificador único.
     * @param idMensaje ID del mensaje a eliminar.
     * @throws RuntimeException Si el mensaje no se encuentra.
     */
    @Transactional
    public void eliminarMensaje(int idMensaje) {
        if (mensajeRepository.existsById(idMensaje)) {
            mensajeRepository.deleteById(idMensaje);
        } else {
            throw new RuntimeException("Mensaje no encontrado con ID: " + idMensaje + " para eliminar.");
        }
    }
}