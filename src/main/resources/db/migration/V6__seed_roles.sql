-- Inserta roles base requeridos para registro de usuarios.
INSERT INTO roles (id, nombre, descripcion, creado_en, actualizado_en)
VALUES
    ('20000000-0000-0000-0000-000000000001', 'ADMIN', 'Administrador del sistema', now(), now()),
    ('20000000-0000-0000-0000-000000000002', 'DOCENTE', 'Rol docente', now(), now()),
    ('20000000-0000-0000-0000-000000000003', 'ESTUDIANTE', 'Rol estudiante', now(), now()),
    ('20000000-0000-0000-0000-000000000004', 'COMISION', 'Miembro de comision', now(), now())
ON CONFLICT (nombre) DO NOTHING;
