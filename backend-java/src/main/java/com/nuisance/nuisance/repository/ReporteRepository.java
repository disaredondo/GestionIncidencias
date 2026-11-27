package com.nuisance.nuisance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import com.nuisance.nuisance.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    boolean existsByCategoriaAndDescripcionIgnoreCaseAndFechaCreacionAfter(
    String categoria, 
    String descripcion, 
    LocalDateTime fechaLimite   
    );
}