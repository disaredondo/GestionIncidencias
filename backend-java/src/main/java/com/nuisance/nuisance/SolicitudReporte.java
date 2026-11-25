package com.nuisance.nuisance;

import lombok.Data;

@Data
public class SolicitudReporte {
    private String categoria;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    
    // Campos específicos de los tipos (los usaremos mañana)
    private Boolean enHorarioNocturno;
    private String tipoResiduo;
    private String tipoElemento;
}