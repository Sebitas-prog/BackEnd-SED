-- Normaliza la tabla de cursos para que coincida con la entidad JPA (nombre y columnas).

-- Renombrar tabla si existe como "curso"
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'curso') THEN
        EXECUTE 'ALTER TABLE curso RENAME TO cursos';
    END IF;
END $$;

-- Asegurar columnas facultad y activo (opcionales en la entidad, pero usadas en el seed)
ALTER TABLE cursos
    ADD COLUMN IF NOT EXISTS facultad VARCHAR(100),
    ADD COLUMN IF NOT EXISTS activo BOOLEAN DEFAULT TRUE;

-- Asegurar unicidad de código
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'uq_cursos_codigo') THEN
        CREATE UNIQUE INDEX uq_cursos_codigo ON cursos(codigo);
    END IF;
END $$;
