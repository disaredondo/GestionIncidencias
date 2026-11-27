package com.nuisance.nuisance.antiabuso;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.repository.IntentoEnvioRepository;
import java.time.LocalDateTime;



@Component
@Order(3)
public class CuotaDiariaRegla implements ReglaAntiAbuso{

    private final IntentoEnvioRepository repository;

    public CuotaDiariaRegla(IntentoEnvioRepository repository) {
    this.repository = repository;
}
    @Override 
    public void validar (SolicitudReporte solicitud, String ipHash){

    if (repository.countByIpHashAndFechaAfter(ipHash, LocalDateTime.now().minusHours(24)) >= 15) {
    throw new IllegalStateException("Has excedido la cuota diaria de reportes D:");

        }
    }
}