package com.SAFE_Rescue.API_Comunicacion.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class BorradorMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_brdr_mensaje;

    @Column(length = 5,nullable = false)
    private int id_brdr_emisor;

    @Column(nullable = false)
    private Date fecha_brdr_mensaje;

    @Column(length = 30,nullable = false)
    private String brdr_titulo;

    @Column(length = 30,nullable = false)
    private String brdr_contenido;

}
