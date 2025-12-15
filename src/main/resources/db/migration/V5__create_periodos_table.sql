-- Crea la tabla que usa la entidad Periodo (periodos) con las columnas esperadas.
-- Esto evita errores 500 al crear periodos desde el frontend.
CREATE TABLE IF NOT EXISTS periodos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(255) NOT NULL UNIQUE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(30) NOT NULL,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT,
    CONSTRAINT chk_periodos_fecha CHECK (fecha_fin > fecha_inicio)
);

CREATE INDEX IF NOT EXISTS idx_periodos_estado ON periodos(estado);
