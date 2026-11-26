package com.nuisance.nuisance.dominio;

public class ReporteBasura extends ReporteDominio {
    private String tipoResiduo;
    private boolean contenedorDesbordado;

    public ReporteBasura(String descripcion, Double latitud, Double longitud, String tipoResiduo, boolean contenedorDesbordado) {
        super(descripcion, latitud, longitud);
        this.tipoResiduo = tipoResiduo;
        this.contenedorDesbordado = contenedorDesbordado;
    }

    @Override
    public void validar() {
        validarComun(); // REUTILIZO EL CODIGO DEL PADRE:0
        
        // Regla específica de la basura
        if (this.tipoResiduo == null || this.tipoResiduo.isBlank()) {
            throw new IllegalArgumentException("El tipo de residuo es obligatorio para reportes de basura");
        }
    }
}