package com.nuisance.nuisance.dominio;
import java.util.Map;

public class ReporteMobiliarioRoto extends ReporteDominio {
    private String tipoElemento;
    private boolean peligroInminente;

    public ReporteMobiliarioRoto(String descripcion, Double latitud, Double longitud, String tipoElemento, boolean peligroInminente) {
        super(descripcion, latitud, longitud);
        this.tipoElemento = tipoElemento;
        this.peligroInminente = peligroInminente;
    }

    @Override
    public void validar() {
        validarComun(); // REUTILIZAMOS EL CODIGO DEL PADRE
        
        // Regla específica del mobiliario
        if (this.tipoElemento == null || this.tipoElemento.isBlank()) {
            throw new IllegalArgumentException("El tipo de elemento (banco, farola...) es obligatorio");
        }
    }

 @Override
    public Map<String, Object> detalles() {
        return Map.of(
            "tipoElemento", tipoElemento != null ? tipoElemento : "", 
            "peligroInminente", peligroInminente
        );
    }
}