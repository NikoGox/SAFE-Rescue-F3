package com.SAFE_Rescue.API_Comunicacion.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Date;


public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_mensaje;

    @Column(length = 5,nullable = false)
    private int id_emisor;

    @Column(length = 5,nullable = false)
    private int id_receptor;

    @Column(nullable = false)
    private Date fecha_mensaje;

    @Column(length = 30,nullable = false)
    private String titulo;

    @Column(length = 200,nullable = false)
    private String contenido;

}
