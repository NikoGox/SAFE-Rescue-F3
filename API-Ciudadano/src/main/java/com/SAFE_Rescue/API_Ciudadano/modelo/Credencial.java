package com.SAFE_Rescue.API_Ciudadano.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credencial_ciudadana")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,length = 80,nullable = false)
    private String correo;

    @Column(length = 16, nullable = false)
    private String contrasenia;

    @Column(length = 1, nullable = true)
    private int intentosFallidos = 0;

    @Column(nullable = false)
    private boolean activo;


}
