package com.nuisance.nuisance.service;

import com.nuisance.nuisance.antiabuso.ReglaAntiAbuso;
import com.nuisance.nuisance.dominio.*;
import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.model.IntentoEnvio;
import com.nuisance.nuisance.model.Reporte;                                             
import com.nuisance.nuisance.repository.IntentoEnvioRepository;
import com.nuisance.nuisance.repository.ReporteRepository;                      // parte 1 mñm termino D:
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository repository;
    private final IntentoEnvioRepository intentoRepository;
    private final List<ReglaAntiAbuso> reglas;

    public ReporteService(
            ReporteRepository repository,
            IntentoEnvioRepository intentoRepository,
            List<ReglaAntiAbuso> reglas) {
        this.repository = repository;
        this.intentoRepository = intentoRepository;
        this.reglas = reglas;
    }

public Reporte crearReporte(SolicitudReporte solicitud, String ipHash) {
    
    if (solicitud.getCategoria() == null || solicitud.getCategoria().isBlank()) {
        throw new IllegalArgumentException("La categoria es obligatoria ");
        }

    //INICIO DE TRY Y CATCH (ES EL PATRON STRATEGY >:)
        try {
            for (ReglaAntiAbuso regla : reglas) {
                regla.validar(solicitud,ipHash);
            }
            IntentoEnvio intentobien = new IntentoEnvio();
            intentobien.setIpHash(ipHash);
            intentobien.setResultado("PERMITIDO");
            intentoRepository.save(intentobien);
        } catch (RuntimeException ex) {
            IntentoEnvio intentomal = new IntentoEnvio();
            intentomal.setIpHash(ipHash);
            intentomal.setResultado("RECHAZADO");
            intentoRepository.save(intentomal);
            throw ex;
        }

        //bye bye patron

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
            default -> throw new IllegalArgumentException("Categoria no valida, ingrese una correcta: " + solicitud.getCategoria());
        };

        //NO TOQUES ESTA PARTEEEE
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