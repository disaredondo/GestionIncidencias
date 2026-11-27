package com.nuisance.nuisance.controller;
import org.springframework.web.bind.annotation.*;

import com.nuisance.nuisance.dto.SolicitudReporte;
import com.nuisance.nuisance.model.Reporte;
import com.nuisance.nuisance.service.ReporteService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping
    public Reporte crearReporte(@RequestBody SolicitudReporte solicitud, HttpServletRequest request) {
        String ipHash = obtenerIpHash(request);
        return service.crearReporte(solicitud, ipHash);
    }
    private String obtenerIpHash(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim(); // Si hay varios proxies, nos quedamos con la primera IP
        }

        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(ip.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al anonimizar la IP", e);
        }
    }
}