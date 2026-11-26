package com.nuisance.nuisance;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping
    public Reporte crearReporte(@RequestBody SolicitudReporte solicitud) {
        return service.crearReporte(solicitud);
    }
}