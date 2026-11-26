package com.nuisance.nuisance;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String descripcion;
    private Double latitud;
    private Double longitud;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Mapeo para la columna JSONB de PostgreSQL
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalles_json", columnDefinition = "jsonb")
    private Map<String, Object> detallesJson;
}