package com.nuisance.nuisance.dominio;

import lombok.Getter;
import java.util.Map;

@Getter
public abstract class ReporteDominio {
    protected String descripcion;
    protected Ubicacion ubicacion;
    protected EstadoReporte estado = EstadoReporte.PENDIENTE;

    public ReporteDominio(String descripcion, Ubicacion ubicacion) {
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    protected void validarComun() {
        if (this.descripcion == null || this.descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (this.descripcion.length() > 280) {
            throw new IllegalArgumentException("La descripción no puede superar los 280 caracteres");
        }
        if (this.ubicacion == null) {
            throw new IllegalArgumentException("La ubicación es obligatoria");
        }
        this.ubicacion.validar();
    }

    public abstract void validar();
    public abstract String categoria();
    public abstract Map<String, Object> detalles();
}