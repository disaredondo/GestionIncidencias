package com.nuisance.nuisance;

import lombok.Data;

@Data
public class SolicitudReporte {
    private String categoria;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    
    // Campos específicos que DEBEN estar aquí
    private Boolean enHorarioNocturno;
    private String tipoResiduo;
    private Boolean contenedorDesbordado;
    private String tipoElemento;
    private Boolean peligroInminente;
}