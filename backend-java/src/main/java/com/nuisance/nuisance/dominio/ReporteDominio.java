package com.nuisance.nuisance.dominio;

public abstract class ReporteDominio {
    protected String descripcion;
    protected Double latitud;
    protected Double longitud;

    public ReporteDominio(String descripcion, Double latitud, Double longitud) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    //la validación compartida por todos
    protected void validarComun() {
        if (this.descripcion == null || this.descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (this.descripcion.length() > 280) {
            throw new IllegalArgumentException("La descripción no puede superar los 280 caracteres");
        }
    }

    // El contrato que deben cumplir todas las hijas
    public abstract void validar(); 
}