package com.SAFE_Rescue.API_Comunicacion.modelo;

import jakarta.persistence.*;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrador_mensaje")
public class BorradorMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_brdr_mensaje;

    @Column(length = 5, nullable = false)
    private int id_brdr_emisor;

    @Column(nullable = false)
    private Date fecha_brdr_mensaje;

    @Column(length = 30, nullable = false)
    private String brdr_titulo;

    @Column(length = 30, nullable = false)
    private String brdr_contenido;

}