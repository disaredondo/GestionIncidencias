package com.nuisance.nuisance;

import com.nuisance.nuisance.dominio.ReporteDominio;
import com.nuisance.nuisance.dominio.ReporteRuido;
import com.nuisance.nuisance.dominio.ReporteBasura;
import com.nuisance.nuisance.dominio.ReporteMobiliarioRoto;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final ReporteRepository repository;

    // Inyección por constructor (la mejor práctica)
    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte crearReporte(SolicitudReporte solicitud) {
        
        // 1. Validación mínima de entrada (necesaria para que el switch no explote)
        if (solicitud.getCategoria() == null || solicitud.getCategoria().isBlank()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        // 2. El Switch moderno de Java (Sin breaks, devuelve un valor directamente)
        ReporteDominio reporteDominio = switch (solicitud.getCategoria().toUpperCase()) {
            case "RUIDO" -> new ReporteRuido(
                solicitud.getDescripcion(), 
                solicitud.getLatitud(), 
                solicitud.getLongitud(), 
                solicitud.getEnHorarioNocturno() != null ? solicitud.getEnHorarioNocturno() : false
            );
            case "BASURA" -> new ReporteBasura(
                solicitud.getDescripcion(), 
                solicitud.getLatitud(), 
                solicitud.getLongitud(),
                solicitud.getTipoResiduo(),
                solicitud.getContenedorDesbordado() != null ? solicitud.getContenedorDesbordado() : false
            );
            case "MOBILIARIO_ROTO" -> new ReporteMobiliarioRoto(
                solicitud.getDescripcion(), 
                solicitud.getLatitud(), 
                solicitud.getLongitud(),
                solicitud.getTipoElemento(),
                solicitud.getPeligroInminente() != null ? solicitud.getPeligroInminente() : false
            );
            default -> throw new IllegalArgumentException("Categoría no válida: " + solicitud.getCategoria());
        };

        // 3. Validación del Dominio (Descripción, coordenadas, reglas específicas)
        reporteDominio.validar();

        // 4. Conversión a Entidad para guardar en la BD
        Reporte entidad = new Reporte();
        entidad.setCategoria(solicitud.getCategoria().toUpperCase());
        entidad.setDescripcion(solicitud.getDescripcion());
        entidad.setLatitud(solicitud.getLatitud());
        entidad.setLongitud(solicitud.getLongitud());
        
        // 5. Polimorfismo puro: El dominio nos da sus detalles para el JSONB
        entidad.setDetallesJson(reporteDominio.detalles());

        // 6. Guardar en Base de Datos
        return repository.save(entidad);
    }
}