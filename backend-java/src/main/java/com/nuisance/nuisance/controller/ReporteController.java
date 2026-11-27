package com.nuisance.nuisance.controller;
import org.springframework.web.bind.annotation.*;

import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.model.Reporte;
import com.nuisance.nuisance.service.ReporteService;

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