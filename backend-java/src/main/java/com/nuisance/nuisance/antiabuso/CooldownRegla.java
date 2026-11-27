package com.nuisance.nuisance.antiabuso;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.repository.IntentoEnvioRepository;
import java.time.LocalDateTime;



@Component
@Order(2)
public class CooldownRegla implements ReglaAntiAbuso{

    private final IntentoEnvioRepository  repository;

    public CooldownRegla(IntentoEnvioRepository repository) {
    this.repository = repository;
}
    @Override 
    public void validar (SolicitudReporte solicitud, String ipHash){

    if (repository.countByIpHashAndFechaAfter(ipHash, LocalDateTime.now().minusMinutes(5)) > 0) {
    throw new IllegalStateException("Espera al menos 5 minutos antes de enviar otro reporte :p");

        }
    }
}