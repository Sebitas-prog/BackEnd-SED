-- PostgreSQL schema for SistemaDeEvaluacionDocente_Redisenado

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

/* =========================
   Identidad y Seguridad
   ========================= */
CREATE TABLE estado_usuario (
    id_estado_usuario UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(30) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE usuario (
    id_usuario UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre_completo VARCHAR(100) NOT NULL,
    correo VARCHAR(254) NOT NULL UNIQUE,
    correo_verificado BOOLEAN NOT NULL DEFAULT FALSE,
    contrasena_hash VARCHAR(255) NOT NULL,
    id_estado_usuario UUID NOT NULL REFERENCES estado_usuario(id_estado_usuario),
    ultimo_acceso TIMESTAMPTZ,
    intentos_fallidos INT NOT NULL DEFAULT 0,
    bloqueado_hasta TIMESTAMPTZ,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_usuario_intentos_non_negative CHECK (intentos_fallidos >= 0)
);

CREATE INDEX idx_usuario_correo ON usuario(correo);
CREATE INDEX idx_usuario_estado ON usuario(id_estado_usuario);

CREATE TABLE token_verificacion (
    id_token UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    token VARCHAR(255) NOT NULL UNIQUE,
    tipo_token VARCHAR(30) NOT NULL,
    expira_en TIMESTAMPTZ NOT NULL,
    usado BOOLEAN NOT NULL DEFAULT FALSE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_token_verificacion_expira CHECK (expira_en > creado_en)
);

CREATE INDEX idx_token_verificacion ON token_verificacion(token);
CREATE INDEX idx_token_usuario_tipo ON token_verificacion(id_usuario, tipo_token, usado);

CREATE TABLE sesion_usuario (
    id_sesion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    token_sesion VARCHAR(255) NOT NULL UNIQUE,
    direccion_ip VARCHAR(45),
    user_agent TEXT,
    expira_en TIMESTAMPTZ NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_sesion_usuario_expira CHECK (expira_en > creado_en)
);

CREATE INDEX idx_sesion_token ON sesion_usuario(token_sesion);
CREATE INDEX idx_sesion_usuario_activo ON sesion_usuario(id_usuario, activo);

CREATE TABLE rol (
    id_rol UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE permiso (
    id_permiso UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    modulo VARCHAR(50),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE rol_permiso (
    id_rol_permiso UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_rol UUID NOT NULL REFERENCES rol(id_rol),
    id_permiso UUID NOT NULL REFERENCES permiso(id_permiso),
    CONSTRAINT uq_rol_permiso UNIQUE (id_rol, id_permiso)
);

CREATE TABLE usuario_rol (
    id_usuario_rol UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    id_rol UUID NOT NULL REFERENCES rol(id_rol),
    asignado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_usuario_rol UNIQUE (id_usuario, id_rol)
);

/* =========================
   Académico
   ========================= */
CREATE TABLE estudiante (
    id_estudiante UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL UNIQUE REFERENCES usuario(id_usuario),
    codigo_estudiante VARCHAR(20) NOT NULL UNIQUE,
    semestre VARCHAR(10) NOT NULL,
    carrera VARCHAR(100) NOT NULL,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_estudiante_codigo ON estudiante(codigo_estudiante);

CREATE TABLE docente (
    id_docente UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL UNIQUE REFERENCES usuario(id_usuario),
    codigo_docente VARCHAR(20) UNIQUE,
    departamento VARCHAR(100) NOT NULL,
    antiguedad INT,
    grado_academico VARCHAR(100),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_docente_codigo ON docente(codigo_docente);

CREATE TABLE curso (
    id_curso UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    facultad VARCHAR(100),
    creditos INT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_curso_creditos_positive CHECK (creditos IS NULL OR creditos > 0)
);

CREATE INDEX idx_curso_codigo ON curso(codigo);
CREATE INDEX idx_curso_facultad ON curso(facultad);

CREATE TABLE estado_periodo (
    id_estado_periodo UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(30) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE periodo (
    id_periodo UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    anio INT NOT NULL,
    termino VARCHAR(20) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_estado_periodo UUID NOT NULL REFERENCES estado_periodo(id_estado_periodo),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_periodo_anio_termino UNIQUE (anio, termino),
    CONSTRAINT chk_periodo_fecha CHECK (fecha_fin > fecha_inicio)
);

CREATE INDEX idx_periodo_estado ON periodo(id_estado_periodo);

CREATE TABLE modalidad_seccion (
    id_modalidad_seccion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(30) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE seccion (
    id_seccion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_curso UUID NOT NULL REFERENCES curso(id_curso),
    id_periodo UUID NOT NULL REFERENCES periodo(id_periodo),
    codigo_seccion VARCHAR(20) NOT NULL,
    id_modalidad_seccion UUID NOT NULL REFERENCES modalidad_seccion(id_modalidad_seccion),
    id_docente_titular UUID NOT NULL REFERENCES docente(id_docente),
    cupo_maximo INT,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_seccion_curso_periodo_codigo UNIQUE (id_curso, id_periodo, codigo_seccion),
    CONSTRAINT chk_seccion_cupo_positive CHECK (cupo_maximo IS NULL OR cupo_maximo > 0)
);

CREATE INDEX idx_seccion_docente ON seccion(id_docente_titular);

CREATE TABLE estado_matricula (
    id_estado_matricula UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(30) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE matricula (
    id_matricula UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_estudiante UUID NOT NULL REFERENCES estudiante(id_estudiante),
    id_seccion UUID NOT NULL REFERENCES seccion(id_seccion),
    fecha_matricula TIMESTAMPTZ NOT NULL DEFAULT now(),
    id_estado_matricula UUID NOT NULL REFERENCES estado_matricula(id_estado_matricula),
    CONSTRAINT uq_matricula_estudiante_seccion UNIQUE (id_estudiante, id_seccion)
);

CREATE INDEX idx_matricula_seccion ON matricula(id_seccion);
CREATE INDEX idx_matricula_estado ON matricula(id_estado_matricula);

/* =========================
   Instrumentos de Evaluación
   ========================= */
CREATE TABLE criterio (
    id_criterio UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE instrumento (
    id_instrumento UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    id_periodo UUID REFERENCES periodo(id_periodo),
    version INT NOT NULL DEFAULT 1,
    vigente BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_instrumento_nombre_version UNIQUE (nombre, version)
);

CREATE INDEX idx_instrumento_vigente ON instrumento(vigente);

CREATE TABLE modulo (
    id_modulo UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_instrumento UUID NOT NULL REFERENCES instrumento(id_instrumento),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    orden INT NOT NULL,
    peso NUMERIC(6,3),
    CONSTRAINT idx_modulo_orden UNIQUE (id_instrumento, orden),
    CONSTRAINT chk_modulo_peso_range CHECK (peso IS NULL OR (peso >= 0 AND peso <= 1))
);

CREATE INDEX idx_modulo_instrumento ON modulo(id_instrumento);

CREATE TABLE pregunta (
    id_pregunta UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_modulo UUID NOT NULL REFERENCES modulo(id_modulo),
    id_criterio UUID REFERENCES criterio(id_criterio),
    enunciado TEXT NOT NULL,
    orden INT NOT NULL,
    peso NUMERIC(6,3),
    es_obligatoria BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT idx_pregunta_orden UNIQUE (id_modulo, orden),
    CONSTRAINT chk_pregunta_peso_range CHECK (peso IS NULL OR (peso >= 0 AND peso <= 1))
);

CREATE INDEX idx_pregunta_modulo ON pregunta(id_modulo);

CREATE TABLE escala (
    id_escala UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE opcion_escala (
    id_opcion_escala UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_escala UUID NOT NULL REFERENCES escala(id_escala),
    valor INT NOT NULL,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT,
    CONSTRAINT uq_opcion_escala_valor UNIQUE (id_escala, valor)
);

CREATE TABLE instrumento_escala (
    id_instrumento_escala UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_instrumento UUID NOT NULL REFERENCES instrumento(id_instrumento),
    id_escala UUID NOT NULL REFERENCES escala(id_escala),
    CONSTRAINT uq_instrumento_escala UNIQUE (id_instrumento, id_escala)
);

/* =========================
   Evaluaciones / Respuestas
   ========================= */
CREATE TABLE estado_evaluacion (
    id_estado_evaluacion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(30) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE canal (
    id_canal UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE evaluacion (
    id_evaluacion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_seccion UUID NOT NULL REFERENCES seccion(id_seccion),
    id_docente UUID NOT NULL REFERENCES docente(id_docente),
    id_instrumento UUID NOT NULL REFERENCES instrumento(id_instrumento),
    id_matricula UUID REFERENCES matricula(id_matricula),
    token_anonimo UUID UNIQUE,
    id_estado_evaluacion UUID NOT NULL REFERENCES estado_evaluacion(id_estado_evaluacion),
    id_canal UUID REFERENCES canal(id_canal),
    comentario_general TEXT,
    iniciada_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    enviada_en TIMESTAMPTZ,
    duracion_segundos INT,
    CONSTRAINT chk_evaluacion_identidad CHECK ((id_matricula IS NULL AND token_anonimo IS NOT NULL) OR (id_matricula IS NOT NULL AND token_anonimo IS NULL)),
    CONSTRAINT chk_evaluacion_duracion_positive CHECK (duracion_segundos IS NULL OR duracion_segundos > 0)
);

CREATE INDEX idx_evaluacion_seccion ON evaluacion(id_seccion);
CREATE INDEX idx_evaluacion_docente ON evaluacion(id_docente);
CREATE INDEX idx_evaluacion_token ON evaluacion(token_anonimo);
CREATE INDEX idx_evaluacion_unica ON evaluacion(id_matricula, id_seccion, id_docente);

CREATE TABLE respuesta (
    id_respuesta UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_evaluacion UUID NOT NULL REFERENCES evaluacion(id_evaluacion),
    id_pregunta UUID NOT NULL REFERENCES pregunta(id_pregunta),
    valor INT NOT NULL,
    comentario TEXT,
    creada_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_respuesta_evaluacion_pregunta UNIQUE (id_evaluacion, id_pregunta)
);

CREATE INDEX idx_respuesta_evaluacion ON respuesta(id_evaluacion);

/* =========================
   Notificaciones y Auditoría
   ========================= */
CREATE TABLE tipo_notificacion (
    id_tipo_notificacion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(50) NOT NULL UNIQUE,
    etiqueta VARCHAR(50) NOT NULL,
    descripcion TEXT
);

CREATE TABLE preferencia_notificacion (
    id_preferencia_notificacion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL UNIQUE REFERENCES usuario(id_usuario),
    preferencias JSONB NOT NULL DEFAULT '{}'::jsonb,
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE notificacion (
    id_notificacion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    id_tipo_notificacion UUID NOT NULL REFERENCES tipo_notificacion(id_tipo_notificacion),
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    tipo_entidad VARCHAR(50),
    id_entidad UUID,
    leido BOOLEAN NOT NULL DEFAULT FALSE,
    fecha TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_notificacion_usuario_leido ON notificacion(id_usuario, leido);
CREATE INDEX idx_notificacion_fecha ON notificacion(fecha);

CREATE TABLE acceso_sistema (
    id_acceso_sistema UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    fecha_acceso TIMESTAMPTZ NOT NULL DEFAULT now(),
    direccion_ip VARCHAR(45),
    user_agent TEXT,
    exitoso BOOLEAN NOT NULL DEFAULT TRUE,
    motivo_fallo VARCHAR(100)
);

CREATE INDEX idx_acceso_usuario ON acceso_sistema(id_usuario);
CREATE INDEX idx_acceso_fecha ON acceso_sistema(fecha_acceso);

CREATE TABLE log_accion (
    id_log_accion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID REFERENCES usuario(id_usuario),
    accion VARCHAR(100) NOT NULL,
    entidad VARCHAR(50),
    id_entidad UUID,
    detalle JSONB,
    direccion_ip VARCHAR(45),
    fecha TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_log_usuario ON log_accion(id_usuario);
CREATE INDEX idx_log_fecha ON log_accion(fecha);
CREATE INDEX idx_log_entidad ON log_accion(entidad, id_entidad);

/* =========================
   Comisión
   ========================= */
CREATE TABLE comision (
    id_comision UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_usuario UUID NOT NULL REFERENCES usuario(id_usuario),
    facultad VARCHAR(100) NOT NULL,
    id_periodo UUID NOT NULL REFERENCES periodo(id_periodo),
    rol_miembro VARCHAR(50) NOT NULL,
    asignado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_comision_usuario_periodo_facultad UNIQUE (id_usuario, id_periodo, facultad)
);

/* =========================
   Configuración
   ========================= */
CREATE TABLE parametro (
    id_parametro UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL UNIQUE,
    valor TEXT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    categoria VARCHAR(50),
    descripcion TEXT,
    es_publico BOOLEAN NOT NULL DEFAULT FALSE,
    modificado_por UUID REFERENCES usuario(id_usuario),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT now(),
    actualizado_en TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_parametro_nombre ON parametro(nombre);
CREATE INDEX idx_parametro_categoria ON parametro(categoria);