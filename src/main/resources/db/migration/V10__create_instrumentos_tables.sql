-- Tablas de instrumentos alineadas con las entidades JPA y lista base 1-5

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS criterios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    peso NUMERIC(6,3),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE TABLE IF NOT EXISTS escalas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(120) NOT NULL UNIQUE,
    descripcion TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE TABLE IF NOT EXISTS opciones_escala (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    valor INT NOT NULL,
    etiqueta VARCHAR(120) NOT NULL,
    escala_id UUID NOT NULL REFERENCES escalas(id) ON DELETE CASCADE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT,
    CONSTRAINT uq_opciones_escala UNIQUE (escala_id, valor)
);

CREATE TABLE IF NOT EXISTS instrumentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(120) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    periodo_id UUID REFERENCES periodos(id) ON DELETE SET NULL,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE TABLE IF NOT EXISTS modulos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(120) NOT NULL,
    descripcion TEXT,
    instrumento_id UUID NOT NULL REFERENCES instrumentos(id) ON DELETE CASCADE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE TABLE IF NOT EXISTS preguntas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    enunciado VARCHAR(500) NOT NULL,
    modulo_id UUID NOT NULL REFERENCES modulos(id) ON DELETE CASCADE,
    criterio_id UUID REFERENCES criterios(id),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE TABLE IF NOT EXISTS instrumentos_escalas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    instrumento_id UUID NOT NULL REFERENCES instrumentos(id) ON DELETE CASCADE,
    escala_id UUID NOT NULL REFERENCES escalas(id) ON DELETE CASCADE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT,
    CONSTRAINT uq_instrumento_escala UNIQUE (instrumento_id, escala_id)
);

-- Datos base: escala Likert 1 a 5
WITH escala_base AS (
    INSERT INTO escalas (id, nombre, descripcion, activo)
    VALUES ('60000000-0000-0000-0000-000000000001', 'Likert 1-5', 'Escala de 1 (Muy deficiente) a 5 (Excelente)', TRUE)
    ON CONFLICT (nombre) DO UPDATE SET descripcion = EXCLUDED.descripcion
    RETURNING id
)
INSERT INTO opciones_escala (valor, etiqueta, escala_id)
SELECT vals.valor, vals.etiqueta, (SELECT id FROM escala_base)
FROM (VALUES
    (1, 'Muy deficiente'),
    (2, 'Deficiente'),
    (3, 'Regular'),
    (4, 'Bueno'),
    (5, 'Excelente')
) AS vals(valor, etiqueta)
ON CONFLICT (escala_id, valor) DO UPDATE SET etiqueta = EXCLUDED.etiqueta;
