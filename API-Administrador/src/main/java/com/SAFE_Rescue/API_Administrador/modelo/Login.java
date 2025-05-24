package com.SAFE_Rescue.API_Administrador.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Login {
    private String correo;
    private String contrasenia;

}
