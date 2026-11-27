package com.nuisance.nuisance.antiabuso;

import com.nuisance.nuisance.dto.SolicitudReporte;

/**
 * ReglaAntiAbuso
 */
public interface ReglaAntiAbuso {
    void validar(SolicitudReporte solicitud,String ipHash);
}