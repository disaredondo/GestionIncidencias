package com.nuisance.nuisance.dominio;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ubicacion {

    private Double latitud;
    private Double longitud;

    public Ubicacion(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void validar() {
        if (this.latitud == null || this.longitud == null) {
            throw new IllegalArgumentException("La latitud y la longitud son obligatorias");
        }
        if (this.latitud < -90.0 || this.latitud > 90.0) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (this.longitud < -180.0 || this.longitud > 180.0) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }
}