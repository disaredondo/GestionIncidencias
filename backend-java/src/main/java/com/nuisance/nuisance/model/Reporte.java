package com.nuisance.nuisance.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

import com.nuisance.nuisance.dominio.Ubicacion;
import com.nuisance.nuisance.dominio.EstadoReporte;

@Data
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String descripcion;
    
    //aca agregamos
    //  las composiciones de ubicacion y de estado  del reporte
    @Embedded
    private Ubicacion ubicacion; 
    
    @Enumerated(EnumType.STRING)
    private EstadoReporte estado;


    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Mapeo para la columna JSONB de PostgreSQL
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalles_json", columnDefinition = "jsonb")
    private Map<String, Object> detallesJson;
}