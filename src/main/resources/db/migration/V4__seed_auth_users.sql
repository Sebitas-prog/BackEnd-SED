-- Seed inicial para autenticacion
-- Crea estados, permisos, roles y usuarios demo para login

-- Estados de usuario
INSERT INTO estados_usuario (id, codigo, descripcion, creado_en, actualizado_en)
VALUES
  ('00000000-0000-0000-0000-000000000001', 'ACTIVO', 'Cuenta activa', now(), now()),
  ('00000000-0000-0000-0000-000000000002', 'PENDIENTE_VERIFICACION', 'Pendiente de verificar correo', now(), now()),
  ('00000000-0000-0000-0000-000000000003', 'BLOQUEADO', 'Cuenta bloqueada', now(), now()),
  ('00000000-0000-0000-0000-000000000004', 'INACTIVO', 'Cuenta inactiva', now(), now())
ON CONFLICT (codigo) DO NOTHING;

-- Permisos base
INSERT INTO permisos (id, codigo, descripcion, creado_en, actualizado_en) VALUES
  ('10000000-0000-0000-0000-000000000001', 'AUTH_USERS_MANAGE', 'Administrar usuarios y roles', now(), now()),
  ('10000000-0000-0000-0000-000000000002', 'DOCENTE_VER_EVALUACIONES', 'Ver evaluaciones asignadas', now(), now()),
  ('10000000-0000-0000-0000-000000000003', 'ESTUDIANTE_RESPONDER', 'Responder instrumentos de evaluacion', now(), now()),
  ('10000000-0000-0000-0000-000000000004', 'COMISION_VER_REPORTES', 'Consultar reportes y periodos', now(), now()),
  ('10000000-0000-0000-0000-000000000005', 'DASHBOARD_GENERAL', 'Acceso a dashboards basicos', now(), now())
ON CONFLICT (codigo) DO NOTHING;

-- Roles
INSERT INTO roles (id, nombre, descripcion, creado_en, actualizado_en) VALUES
  ('20000000-0000-0000-0000-000000000001', 'ADMIN', 'Administrador del sistema', now(), now()),
  ('20000000-0000-0000-0000-000000000002', 'DOCENTE', 'Rol docente', now(), now()),
  ('20000000-0000-0000-0000-000000000003', 'ESTUDIANTE', 'Rol estudiante', now(), now()),
  ('20000000-0000-0000-0000-000000000004', 'COMISION', 'Miembro de comision', now(), now())
ON CONFLICT (nombre) DO NOTHING;

-- Rol-Permiso
INSERT INTO roles_permisos (id, rol_id, permiso_id, creado_en, actualizado_en) VALUES
  ('30000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001', now(), now()),
  ('30000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000002', now(), now()),
  ('30000000-0000-0000-0000-000000000003', '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000003', now(), now()),
  ('30000000-0000-0000-0000-000000000004', '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000004', now(), now()),
  ('30000000-0000-0000-0000-000000000005', '20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000005', now(), now()),
  ('30000000-0000-0000-0000-000000000006', '20000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000002', now(), now()),
  ('30000000-0000-0000-0000-000000000007', '20000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000005', now(), now()),
  ('30000000-0000-0000-0000-000000000008', '20000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003', now(), now()),
  ('30000000-0000-0000-0000-000000000009', '20000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000005', now(), now()),
  ('30000000-0000-0000-0000-00000000000A', '20000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000004', now(), now()),
  ('30000000-0000-0000-0000-00000000000B', '20000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000005', now(), now())
ON CONFLICT (rol_id, permiso_id) DO NOTHING;

-- Usuarios demo (password: Admin123!)
INSERT INTO usuarios (
    id_usuario, nombre_completo, correo, correo_verificado,
    contrasena_hash, estado, ultimo_acceso, intentos_fallidos, bloqueado_hasta,
    creado_en, actualizado_en
) VALUES
  ('40000000-0000-0000-0000-000000000001', 'Admin General', 'admin@sed.test', true, '$2b$12$v.VxOBmgfxzJlxpSsWqjw.fS4ZwR1r929eCRCG/3X48JtruOgIIDG', 'ACTIVO', now(), 0, NULL, now(), now()),
  ('40000000-0000-0000-0000-000000000002', 'Docente Demo', 'docente@sed.test', true, '$2b$12$v.VxOBmgfxzJlxpSsWqjw.fS4ZwR1r929eCRCG/3X48JtruOgIIDG', 'ACTIVO', now(), 0, NULL, now(), now()),
  ('40000000-0000-0000-0000-000000000003', 'Estudiante Demo', 'estudiante@sed.test', true, '$2b$12$v.VxOBmgfxzJlxpSsWqjw.fS4ZwR1r929eCRCG/3X48JtruOgIIDG', 'ACTIVO', now(), 0, NULL, now(), now()),
  ('40000000-0000-0000-0000-000000000004', 'Comision Demo', 'comision@sed.test', true, '$2b$12$v.VxOBmgfxzJlxpSsWqjw.fS4ZwR1r929eCRCG/3X48JtruOgIIDG', 'ACTIVO', now(), 0, NULL, now(), now())
ON CONFLICT (correo) DO NOTHING;

-- Usuario-Rol
INSERT INTO usuarios_roles (id, usuario_id, rol_id, creado_en, actualizado_en) VALUES
  ('50000000-0000-0000-0000-000000000001', '40000000-0000-0000-0000-000000000001', '20000000-0000-0000-0000-000000000001', now(), now()),
  ('50000000-0000-0000-0000-000000000002', '40000000-0000-0000-0000-000000000002', '20000000-0000-0000-0000-000000000002', now(), now()),
  ('50000000-0000-0000-0000-000000000003', '40000000-0000-0000-0000-000000000003', '20000000-0000-0000-0000-000000000003', now(), now()),
  ('50000000-0000-0000-0000-000000000004', '40000000-0000-0000-0000-000000000004', '20000000-0000-0000-0000-000000000004', now(), now())
ON CONFLICT (usuario_id, rol_id) DO NOTHING;
