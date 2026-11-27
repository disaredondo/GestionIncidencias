package com.nuisance.nuisance.service;

import com.nuisance.nuisance.dominio.*;
import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.model.Reporte;
import com.nuisance.nuisance.repository.ReporteRepository;

import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final ReporteRepository repository;

    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte crearReporte(SolicitudReporte solicitud) {
        if (solicitud.getCategoria() == null || solicitud.getCategoria().isBlank()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        Ubicacion ubicacion = new Ubicacion(
            solicitud.getLatitud(),
            solicitud.getLongitud()
        );

        ReporteDominio reporteDominio = switch (solicitud.getCategoria().trim().toUpperCase()) {
            case "RUIDO" -> new ReporteRuido(
                solicitud.getDescripcion(),
                ubicacion,
                solicitud.getEnHorarioNocturno() != null ? solicitud.getEnHorarioNocturno() : false
            );
            case "BASURA" -> new ReporteBasura(
                solicitud.getDescripcion(),
                ubicacion,
                solicitud.getTipoResiduo(),
                solicitud.getContenedorDesbordado() != null ? solicitud.getContenedorDesbordado() : false
            );
            case "MOBILIARIO_ROTO" -> new ReporteMobiliarioRoto(
                solicitud.getDescripcion(),
                ubicacion,
                solicitud.getTipoElemento(),
                solicitud.getPeligroInminente() != null ? solicitud.getPeligroInminente() : false
            );
            default -> throw new IllegalArgumentException("Categoría no válida: " + solicitud.getCategoria());
        };

        reporteDominio.validar();

        Reporte entidad = new Reporte();
        entidad.setCategoria(reporteDominio.categoria());
        entidad.setDescripcion(reporteDominio.getDescripcion());
        entidad.setUbicacion(reporteDominio.getUbicacion());
        entidad.setEstado(reporteDominio.getEstado());
        entidad.setDetallesJson(reporteDominio.detalles());

        return repository.save(entidad);
    }
}