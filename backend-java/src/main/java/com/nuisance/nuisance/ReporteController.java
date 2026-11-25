package com.nuisance.nuisance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository repository;

    @PostMapping
    public Reporte crearReporte(@RequestBody Reporte reporte) {
        return repository.save(reporte);
    }
}