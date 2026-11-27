package com.nuisance.nuisance.dominio;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor //para q cree un constructor vacion para hibernate
@AllArgsConstructor  

public class Ubicacion {
    Double latitud;  
    Double longitud;

    public void validar() {
        if (latitud == null || longitud == null) {
            throw new IllegalArgumentException("Las coordenadas son obligatorias");
        }
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }
}