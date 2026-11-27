package com.nuisance.nuisance;

import com.nuisance.nuisance.dominio.ReporteDominio;
import com.nuisance.nuisance.dominio.ReporteRuido;
import com.nuisance.nuisance.dominio.ReporteBasura;
import com.nuisance.nuisance.dominio.ReporteMobiliarioRoto;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.nuisance.nuisance.dominio.Ubicacion;
import com.nuisance.nuisance.dominio.EstadoReporte;
@Service
public class ReporteService {

    private final ReporteRepository repository;

    // Inyección por constructor (la mejor practica)
    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

        public Reporte crearReporte(SolicitudReporte solicitud) {
        
        if (solicitud.getCategoria() == null || solicitud.getCategoria().isBlank()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        // 1. Construimos la Ubicación con los datos del DTO
        Ubicacion ubicacion = new Ubicacion(
            solicitud.getLatitud(), 
            solicitud.getLongitud() 
        );

        // 2. El Switch (Ahora le pasamos el objeto 'ubicacion' que acabamos de crear)
        ReporteDominio reporteDominio = switch (solicitud.getCategoria().toUpperCase()) {
            case "RUIDO" -> new ReporteRuido(
                solicitud.getDescripcion(), 
                ubicacion, // <-- Usamos el objeto
                solicitud.getEnHorarioNocturno() != null ? solicitud.getEnHorarioNocturno() : false
            );
            case "BASURA" -> new ReporteBasura(
                solicitud.getDescripcion(), 
                ubicacion, // <-- Usamos el objeto
                solicitud.getTipoResiduo(),
                solicitud.getContenedorDesbordado() != null ? solicitud.getContenedorDesbordado() : false
            );
            case "MOBILIARIO_ROTO" -> new ReporteMobiliarioRoto(
                solicitud.getDescripcion(), 
                ubicacion, // <-- Usamos el objeto
                solicitud.getTipoElemento(),
                solicitud.getPeligroInminente() != null ? solicitud.getPeligroInminente() : false
            );
            default -> throw new IllegalArgumentException("Categoría no válida: " + solicitud.getCategoria());
        };

        // 3. Validación
        reporteDominio.validar();

        // 4. Conversión a Entidad
        Reporte entidad = new Reporte();
        entidad.setCategoria(solicitud.getCategoria().toUpperCase());
        entidad.setDescripcion(solicitud.getDescripcion());
        
        //  LA COMPOSICIÓN
        entidad.setUbicacion(ubicacion); 
        
        // Estado por defecto
        entidad.setEstado(EstadoReporte.PENDIENTE);

        // 5. Polimorfismo
        entidad.setDetallesJson(reporteDominio.detalles());

        return repository.save(entidad);
    }
}