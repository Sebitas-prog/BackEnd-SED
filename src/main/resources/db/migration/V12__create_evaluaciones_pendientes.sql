CREATE TABLE IF NOT EXISTS evaluaciones_pendientes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    matricula_id UUID NOT NULL REFERENCES matriculas(id) ON DELETE CASCADE,
    seccion_id UUID NOT NULL REFERENCES secciones(id) ON DELETE CASCADE,
    instrumento_id UUID NOT NULL REFERENCES instrumentos(id) ON DELETE CASCADE,
    estado VARCHAR(30) NOT NULL,
    completado_en TIMESTAMPTZ,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT
);

CREATE INDEX IF NOT EXISTS idx_evalpend_matricula ON evaluaciones_pendientes(matricula_id);
CREATE INDEX IF NOT EXISTS idx_evalpend_seccion ON evaluaciones_pendientes(seccion_id);
CREATE INDEX IF NOT EXISTS idx_evalpend_instrumento ON evaluaciones_pendientes(instrumento_id);
CREATE INDEX IF NOT EXISTS idx_evalpend_estado ON evaluaciones_pendientes(estado);
