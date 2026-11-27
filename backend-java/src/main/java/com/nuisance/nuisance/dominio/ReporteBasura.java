package com.nuisance.nuisance.dominio;

import java.util.Map;

public class ReporteBasura extends ReporteDominio {
    private String tipoResiduo;
    private boolean contenedorDesbordado;

    public ReporteBasura(String descripcion, Ubicacion ubicacion, String tipoResiduo, boolean contenedorDesbordado) {
        super(descripcion, ubicacion);
        this.tipoResiduo = tipoResiduo;
        this.contenedorDesbordado = contenedorDesbordado;
    }

    @Override
    public void validar() {
        validarComun(); 
        if (this.tipoResiduo == null || this.tipoResiduo.isBlank()) {
            throw new IllegalArgumentException("El tipo de residuo es obligatorio para reportes de basura");
        }
    }

    @Override
    public Map<String, Object> detalles() {
        return Map.of(
            "tipoResiduo", tipoResiduo != null ? tipoResiduo : "", 
            "contenedorDesbordado", contenedorDesbordado
        );
    }
}