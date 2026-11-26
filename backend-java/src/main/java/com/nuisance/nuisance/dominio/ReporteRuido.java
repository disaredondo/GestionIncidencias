package com.nuisance.nuisance.dominio;
import java.util.Map;


public class ReporteRuido extends ReporteDominio {
    private boolean enHorarioNocturno;

    public ReporteRuido(String descripcion, Double latitud, Double longitud, boolean enHorarioNocturno) {
        super(descripcion, latitud, longitud);
        this.enHorarioNocturno = enHorarioNocturno;
    }

    @Override
    public void validar() {
        validarComun(); //REUTILIZAMOS EL COODIGO DEL PADRE
        // maybe si luego quiero agregar alguna validacion especifica aca la pongo ;) estara en mejoras a futuro

    }
    
 @Override
    public Map<String, Object> detalles() {
        return Map.of(
            "enHorarioNocturno", enHorarioNocturno // Así de simple
        );
    }
}