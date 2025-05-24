package com.SAFE_Rescue.API_Administrador.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Bombero")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bombero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,length = 8,nullable = false)
    private Long run;

    @Column(length = 1,nullable = false)
    private String dv;

    @Column(length = 50,nullable = false)
    private String nombre;

    @Column(length = 50,nullable = false)
    private String a_paterno;

    @Column(length = 50,nullable = false)
    private String a_materno;

    @Column(nullable = false)
    private Date fecha_registro;

    @Column(unique = true,length = 9,nullable = false)
    private Long telefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credenciales_id", referencedColumnName = "id")
    private Credencial credencial;

}
