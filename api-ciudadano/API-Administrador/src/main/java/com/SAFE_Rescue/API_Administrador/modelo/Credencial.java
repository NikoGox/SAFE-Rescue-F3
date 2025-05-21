package com.SAFE_Rescue.API_Administrador.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Credencial")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16, nullable = false)
    private String contrasenia;

    @Column
    private int intentosFallidos = 0;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private Estado estado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

}
