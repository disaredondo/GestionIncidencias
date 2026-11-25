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

    //ODO reporte debe saber validarse
    public abstract void validar(); 
}