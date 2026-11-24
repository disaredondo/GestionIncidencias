# Nuisance - — Sistema de detección de zonas problemáticas a partir de reportes ciudadanos
Sistema modular de recepción y análisis de incidencias urbanas con Java, PostgreSQL y Python 

Tecnologías principales
- Backend:Java con diseño orientado a objetos y patrón Strategy.
- Base de datos:PostgreSQL con aislamiento de usuarios, mínimo privilegio e inmutabilidad de datos
- Análisis:Python para procesamiento de datos y detección de tendencias.
- Interfaz:Streamlit.
- Despliegue:Docker Compose (entorno contenedorizado).

> **Estado:** En desarrollo — Fase inicial (estructura e infraestructura)

Sistema para recoger reportes anónimos de incidencias urbanas (ruido, basura y mobiliario roto) y analizarlos automáticamente para detectar zonas problemáticas y tendencias. 
Proyecto de portafolio (90 h — IFCT0609) centrado en diseño orientado a objetos en Java y despliegue modular con Docker.

## Arquitectura (5 contenedores)

| Servicio | Tecnología | Función |
| :--- | :--- | :--- |
| **`nginx`** | Nginx | *Reverse proxy* de entrada; añade `X-Forwarded-For` con la IP real. |
| **`dashboard`** | Python (Streamlit) | Formulario, mapa y métricas. Solo se comunica con la API de Java. |
| **`backend-java`** | Java (Spring Boot) | Puerta única de datos: validación OO, anti-abuso (HTTP 429) y lectura de resultados. |
| **`db`** | PostgreSQL | Persistencia de reportes, intentos de envío (hash IP) y resultados analíticos. |
| **`analisis-python`** | Python | Proceso periódico: clustering geográfico, anomalías e IA ligera. |