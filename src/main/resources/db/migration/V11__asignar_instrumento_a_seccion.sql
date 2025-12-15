-- Permite asignar instrumentos a cursos/secciones
ALTER TABLE secciones
    ADD COLUMN IF NOT EXISTS instrumento_id UUID REFERENCES instrumentos(id);
