package com.nuisance.nuisance.antiabuso;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.repository.ReporteRepository;
import java.time.LocalDateTime;



@Component
@Order(4)
public class DuplicadoRegla implements ReglaAntiAbuso{

    private final ReporteRepository repository;

    public DuplicadoRegla(ReporteRepository repository) {
    this.repository = repository;
}
@Override
    public void validar(SolicitudReporte solicitud, String ipHash) {
        boolean yaExiste = repository.existsByCategoriaAndDescripcionIgnoreCaseAndFechaCreacionAfter(
            solicitud.getCategoria().trim().toUpperCase(),
            solicitud.getDescripcion() != null ? solicitud.getDescripcion().trim() : "",
            LocalDateTime.now().minusHours(24)
        );

        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un reporte idéntico registrado recientemente");
        }
    }
}