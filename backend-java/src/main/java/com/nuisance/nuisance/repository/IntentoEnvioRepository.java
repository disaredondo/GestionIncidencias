package com.nuisance.nuisance.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nuisance.nuisance.model.IntentoEnvio;

@Repository
public interface IntentoEnvioRepository extends JpaRepository<IntentoEnvio, Long> {
    Optional<IntentoEnvio> findFirstByIpHashOrderByFechaDesc(String ipHash);

    long countByIpHashAndFechaAfter(String ipHash, LocalDateTime fechaLimite);
}