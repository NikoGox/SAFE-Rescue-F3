package com.SAFE_Rescue.API_Comunicacion.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensaje")
public class Mensaje {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private int idMensaje;

    @Column(name = "id_emisor", nullable = false)
    private int idEmisor;

    @Column(name = "id_receptor", nullable = false)
    private int idReceptor;

    @Column(name = "fecha_mensaje", nullable = false)
    private Date fechaMensaje;

    @Column(name = "titulo", length = 30, nullable = false)
    private String titulo;

    @Column(name = "contenido", length = 250, nullable = false)
    private String contenido;

    /**
     * Referencia al borrador original a partir del cual se creó este mensaje.
     * Se ignora en la serialización JSON para evitar recursión.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_borrador_original", referencedColumnName = "id_brdr_mensaje", nullable = true)
    @JsonIgnore
    private BorradorMensaje borradorOriginal;
}