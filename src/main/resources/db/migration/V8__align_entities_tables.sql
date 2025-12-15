-- Tablas alineadas con las entidades JPA (nombres y columnas esperadas)

-- Estudiantes
CREATE TABLE IF NOT EXISTS estudiantes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID UNIQUE,
    nombres VARCHAR(100) NOT NULL DEFAULT '',
    apellidos VARCHAR(100) NOT NULL DEFAULT '',
    email VARCHAR(254) NOT NULL UNIQUE,
    codigo_estudiante VARCHAR(20) UNIQUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Docentes
CREATE TABLE IF NOT EXISTS docentes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID UNIQUE,
    nombres VARCHAR(100) NOT NULL DEFAULT '',
    apellidos VARCHAR(100) NOT NULL DEFAULT '',
    email VARCHAR(254) NOT NULL UNIQUE,
    codigo_docente VARCHAR(20) UNIQUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Secciones
CREATE TABLE IF NOT EXISTS secciones (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL,
    modalidad VARCHAR(30) NOT NULL DEFAULT 'PRESENCIAL',
    curso_id UUID NOT NULL REFERENCES cursos(id),
    periodo_id UUID NOT NULL REFERENCES periodos(id),
    docente_id UUID REFERENCES docentes(id),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_seccion_curso_periodo UNIQUE (curso_id, periodo_id)
);

-- Matriculas
CREATE TABLE IF NOT EXISTS matriculas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estudiante_id UUID NOT NULL REFERENCES estudiantes(id),
    seccion_id UUID NOT NULL REFERENCES secciones(id),
    estado VARCHAR(30) NOT NULL DEFAULT 'INSCRITO',
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_matricula_estudiante_seccion UNIQUE (estudiante_id, seccion_id)
);

-- Índices auxiliares
CREATE INDEX IF NOT EXISTS idx_secciones_periodo ON secciones(periodo_id);
CREATE INDEX IF NOT EXISTS idx_secciones_docente ON secciones(docente_id);
CREATE INDEX IF NOT EXISTS idx_matriculas_seccion ON matriculas(seccion_id);
CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id);
