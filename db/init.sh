#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 \
     --username "$POSTGRES_USER" \
     --dbname "$POSTGRES_DB" \
     -v java_pass="$JAVA_DB_PASSWORD" \
     -v python_pass="$PYTHON_DB_PASSWORD" <<-EOSQL

-- 1. CREACIÓN DE TABLAS
CREATE TABLE IF NOT EXISTS reportes (
    id BIGSERIAL PRIMARY KEY,
    categoria VARCHAR(50) NOT NULL CHECK (categoria IN ('RUIDO', 'BASURA', 'MOBILIARIO_ROTO')),
    descripcion VARCHAR(280) NOT NULL,
    latitud DOUBLE PRECISION NOT NULL,
    longitud DOUBLE PRECISION NOT NULL,
    direccion_aproximada VARCHAR(200),
    etiquetas TEXT[],
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'EN_REVISION', 'RESUELTO')),
    detalles_json JSONB,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS intentos_envio (
    id BIGSERIAL PRIMARY KEY,
    ip_hash VARCHAR(64) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resultado VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS analisis_resultados (
    id BIGSERIAL PRIMARY KEY,
    tipo_analisis VARCHAR(50) NOT NULL,
    datos_json JSONB NOT NULL,
    fecha_generacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_reportes_ubicacion
    ON reportes (latitud, longitud);

CREATE INDEX IF NOT EXISTS idx_reportes_categoria_fecha
    ON reportes (categoria, fecha_creacion);

-- 2. USUARIOS Y PERMISOS (MÍNIMO PRIVILEGIO E INMUTABILIDAD)
CREATE USER api_java WITH PASSWORD :'java_pass';
CREATE USER analisis_python WITH PASSWORD :'python_pass';

GRANT CONNECT ON DATABASE "$POSTGRES_DB" TO api_java, analisis_python;
GRANT USAGE ON SCHEMA public TO api_java, analisis_python;

-- api_java: solo lectura e inserción (sin UPDATE ni DELETE)
GRANT SELECT, INSERT ON TABLE reportes, intentos_envio TO api_java;
GRANT SELECT ON TABLE analisis_resultados TO api_java;
GRANT USAGE, SELECT ON SEQUENCE reportes_id_seq, intentos_envio_id_seq TO api_java;

-- analisis_python: solo lectura en reportes, lectura/inserción en analisis_resultados
GRANT SELECT ON TABLE reportes TO analisis_python;
GRANT SELECT, INSERT ON TABLE analisis_resultados TO analisis_python;
GRANT USAGE, SELECT ON SEQUENCE analisis_resultados_id_seq TO analisis_python;
REVOKE ALL ON TABLE intentos_envio FROM analisis_python; 
EOSQL