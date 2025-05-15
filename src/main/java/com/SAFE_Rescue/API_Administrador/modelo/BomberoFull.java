package com.SAFE_Rescue.API_Administrador.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BomberoFull {
    private Bombero bombero;
    private Credencial credencial;
    private Rol rol;
}
