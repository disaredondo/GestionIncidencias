package com.nuisance.nuisance.dominio;

import java.util.Map;

public abstract class ReporteDominio {
    protected String descripcion;
    protected Ubicacion ubicacion;

    public ReporteDominio(String descripcion,Ubicacion ubicacion) {
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
        // delego la validación de la ubicación a la clase Ubicacion
        if (this.ubicacion == null) {
            throw new IllegalArgumentException("La ubicación es obligatoria");
        }
        this.ubicacion.validar(); 
    }

    public abstract void validar();
    
    // El nuevo metodo polimórfico
    public abstract Map<String, Object> detalles(); 
}
    