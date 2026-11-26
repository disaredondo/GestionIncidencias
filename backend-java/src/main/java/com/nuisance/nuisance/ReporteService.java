package com.nuisance.nuisance;

import com.nuisance.nuisance.dominio.ReporteDominio;
import com.nuisance.nuisance.dominio.ReporteRuido;
import com.nuisance.nuisance.dominio.ReporteBasura; // <-- Importante
import com.nuisance.nuisance.dominio.ReporteMobiliarioRoto; // <-- Importante
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final ReporteRepository repository;

    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte crearReporte(SolicitudReporte solicitud) {
        ReporteDominio reporteDominio;
        
        // EL SWITCH: Decide qué subclase crear
        switch (solicitud.getCategoria().toUpperCase()) {
            case "RUIDO":
                reporteDominio = new ReporteRuido(
                    solicitud.getDescripcion(), 
                    solicitud.getLatitud(), 
                    solicitud.getLongitud(), 
                    solicitud.getEnHorarioNocturno() != null ? solicitud.getEnHorarioNocturno() : false
                );
                break;
            case "BASURA":
                reporteDominio = new ReporteBasura(
                    solicitud.getDescripcion(), 
                    solicitud.getLatitud(), 
                    solicitud.getLongitud(),
                    solicitud.getTipoResiduo(), // Aquí sí va el tipoResiduo
                    solicitud.getContenedorDesbordado() != null ? solicitud.getContenedorDesbordado() : false
                );
                break;
            case "MOBILIARIO_ROTO":
                reporteDominio = new ReporteMobiliarioRoto(
                    solicitud.getDescripcion(), 
                    solicitud.getLatitud(), 
                    solicitud.getLongitud(),
                    solicitud.getTipoElemento(),
                    solicitud.getPeligroInminente() != null ? solicitud.getPeligroInminente() : false
                );
                break;
            default:
                throw new IllegalArgumentException("Categoría no válida: " + solicitud.getCategoria());
        }

        // Validamos
        reporteDominio.validar();

        // Convertimos a Entidad
        Reporte entidad = new Reporte();
        entidad.setCategoria(solicitud.getCategoria().toUpperCase());
        entidad.setDescripcion(solicitud.getDescripcion());
        entidad.setLatitud(solicitud.getLatitud());
        entidad.setLongitud(solicitud.getLongitud());

        // Guardamos
        return repository.save(entidad);
    }
}