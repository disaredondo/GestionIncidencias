package com.nuisance.nuisance.antiabuso;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.nuisance.nuisance.dto.SolicitudReporte;

@Component
@Order(1)
public class HoneypotRegla implements ReglaAntiAbuso{

    @Override
     public void validar(SolicitudReporte solicitud, String ipHash){

        if (solicitud.getCampoTrampa() !=null && ! solicitud.getCampoTrampa().isBlank()){
            throw new IllegalArgumentException("Se detecto un intento de abuso >;(");

        }
    }
}