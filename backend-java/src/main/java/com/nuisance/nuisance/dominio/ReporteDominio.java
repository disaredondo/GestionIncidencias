package com.nuisance.nuisance.dominio;

import java.util.Map;

public abstract class ReporteDominio {
    protected String descripcion;
    protected Double latitud;
    protected Double longitud;

    public ReporteDominio(String descripcion, Double latitud, Double longitud) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    protected void validarComun() {
        if (this.descripcion == null || this.descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (this.descripcion.length() > 280) {
            throw new IllegalArgumentException("La descripción no puede superar los 280 caracteres");
        }
        if (this.latitud == null || this.longitud == null) {
            throw new IllegalArgumentException("Las coordenadas son obligatorias");
        }
        if (this.latitud < -90 || this.latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (this.longitud < -180 || this.longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }

    public abstract void validar();
    
    // El nuevo método polimórfico
    public abstract Map<String, Object> detalles(); 
}