package com.nuisance.nuisance.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "intentos_envio")
public class IntentoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_hash", nullable = false)
    private String ipHash;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private String resultado; // aun no lo tenemos pero seguramente sean "PERMITIDO", "RECHAZADO_COOLDOWN", "RECHAZADO_CUOTA"
}



