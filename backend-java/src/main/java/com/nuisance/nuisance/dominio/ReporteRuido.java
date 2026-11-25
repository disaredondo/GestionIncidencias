package com.nuisance.nuisance.dominio;

public class ReporteRuido extends ReporteDominio {
    private boolean enHorarioNocturno;

    public ReporteRuido(String descripcion, Double latitud, Double longitud, boolean enHorarioNocturno) {
        super(descripcion, latitud, longitud);
        this.enHorarioNocturno = enHorarioNocturno;
    }

    @Override
    public void validar() {
        if (this.descripcion == null || this.descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (this.descripcion.length() > 280) {
            throw new IllegalArgumentException("La descripción no puede superar los 280 caracteres");
        }
    }
}