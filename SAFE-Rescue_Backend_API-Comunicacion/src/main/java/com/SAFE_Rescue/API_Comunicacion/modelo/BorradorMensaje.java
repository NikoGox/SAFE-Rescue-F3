package com.SAFE_Rescue.API_Comunicacion.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrador_mensaje")
public class BorradorMensaje {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brdr_mensaje")
    private int idBrdrMensaje;

    @Column(name = "id_brdr_emisor", nullable = false)
    private int idBrdrEmisor;

    @Column(name = "fecha_brdr_mensaje", nullable = false)
    private Date fechaBrdrMensaje;

    @Column(name = "brdr_titulo", length = 30, nullable = false)
    private String brdrTitulo;

    @Column(name = "brdr_contenido", length = 250, nullable = false)
    private String brdrContenido;

    /**
     * Indica si el borrador ya ha sido enviado como mensaje.
     * Un borrador enviado no debería ser modificado o re-enviado directamente.
     */
    @Column(name = "borrador_enviado", nullable = false)
    private boolean borradorEnviado;
}