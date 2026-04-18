# Backend – SED (Sistema de Evaluación Docente)

API Spring Boot para periodos, secciones, instrumentos (formularios), matrículas y evaluaciones docentes. Integra JWT, Flyway y métricas por rol (estudiante, docente, comisión).

---

## 📋 Descripción
Servicio REST que:
- Gestiona periodos y secciones con docentes asignados.
- Permite a la comisión crear instrumentos (módulos + preguntas) y asignarlos a secciones.
- Genera pendientes de evaluación para estudiantes matriculados y recibe sus respuestas.
- Calcula resúmenes para docentes y métricas de avance para la comisión.

---

## 🏗️ Arquitectura y estructura
Paquetes (src/main/java/com/sed/backend):
- `config/` – Seguridad (JWT), CORS.
- `domain/entities/` – Modelos JPA (Periodo, Seccion, Instrumento, Modulo, Pregunta, Matricula, Evaluacion, EvaluacionPendiente, Usuario, Docente, Estudiante).
- `domain/enums/` – Estados y catálogos (Modalidad, EstadoEvaluacionPendienteEnum, etc.).
- `application/dto/request|response/` – DTOs de entrada/salida.
- `application/usecases/` – Casos de uso (servicios de aplicación). Ej:
  - `evaluacion/AsignarInstrumentoASeccionUseCase.java` (crea pendientes al asignar).
  - `evaluacion/CreateEvaluacionUseCase.java` (registra respuestas y completa pendiente).
  - `evaluacion/GetResumenEvaluacionDocenteUseCase.java` (promedios y detalle anónimo).
  - `evaluacion/GetMetricasPeriodoUseCase.java` (tasa respuesta, progreso).
  - `curso/GetCursosEstudianteUseCase.java`, `curso/GetCursosDocenteUseCase.java`.
- `infrastructure/implementations/` – Servicios concretos (MatriculaServiceImpl, SeccionServiceImpl, InstrumentoServiceImpl, etc.).
- `infrastructure/persistence/repositories/` – Repos JPA (EvaluacionRepository, EvaluacionPendienteRepository, SeccionRepository…).
- `presentation/controllers/` – Endpoints agrupados por rol/módulo.
- `resources/db/migration/` – Migraciones Flyway V10–V12 (instrumentos, asignación a sección, evaluaciones_pendientes) y semillas.

---

## 🔐 Seguridad
- JWT en `config/SecurityConfig.java` con filtro `JwtAuthenticationFilter`.
- Rutas públicas: `/api/auth/**`, `/api/periodos/**` (lectura), y algunas de curso/sección según configuración actual.
- CORS en `config/CorsConfig.java`.

---

## 🚀 Cómo correr (dev)
Requisitos: JDK 17, Maven, PostgreSQL.

1) Variables/`.env`:

```
APPLICATION_NAME=sed

DB_URL=jdbc:postgresql://localhost:5432/{nombre-db-aqui}
DB_USERNAME={usuario-postgres-aqui}
DB_USER_PASSWORD={contraseña-usuario-postgres-aqui}

JPA_HIBERNATE_DDL_AUTO=create-drop

# JWT Secret - Debe ser Base64 y tener al menos 256 bits (32 bytes)
# Este secret es SOLO para desarrollo - Cambia en producción
security.jwt.secret={secret-aqui-256-bits}
security.jwt.access-token-validity-ms=900000
security.jwt.refresh-token-validity-ms=604800000

# Configuracion de Redis
REDIS_HOST={ip-redis-aqui}
REDIS_PORT={puerto-redis-aqui}
```

2) Migraciones: Flyway se ejecuta al iniciar; verifica `db/migration/V10__...`, `V11__...`, `V12__...`.
3) Tener `Redis` corriendo en tu máquina, para esto puedes usar `Docker`:

```bash
docker run -d -p 6379:6379 --name redis redis
```

Verifica:

```bash
docker ps
```

Prueba la conexión:

```bash
docker exec -it redis redis-cli
ping
# Debe responder: PONG
```

1) Ejecutar:
```bash
mvn spring-boot:run
# o
mvn -DskipTests compile
java -jar target/backend-0.0.1-SNAPSHOT.jar
```
1) Salud: `GET http://localhost:8080/actuator/health`.

---

## 🌐 Endpoints relevantes
- Auth: `/api/auth/**`
- Periodos/Secciones (comisión): `/api/periodos/**`, `/api/comision/periodos/{id}/secciones`
- Instrumentos (comisión): `GET/POST/PUT /api/instrumentos`, `POST /api/instrumentos/asignar`
- Cursos por rol:
  - Estudiante: `/api/estudiante/cursos?estudianteId&periodoId`
  - Docente: `/api/docente/cursos?docenteId&periodoId`
- Evaluaciones:
  - Enviar respuestas: `POST /api/evaluaciones` (usa `matriculaId`, `instrumentoId`, `respuestas[preguntaId, valor, comentario]`)
  - Resumen docente: `GET /api/docente/evaluaciones/resumen?seccionId=...`
- Métricas comisión: `/api/comision/periodos/{periodoId}/metricas`

---

## 🧩 Flujo de evaluación (implementación)
1) Comisión crea Instrumento (`InstrumentoController`) y lo asigna a una Sección (`AsignarInstrumentoASeccionUseCase`).
2) Al asignar, `SeccionServiceImpl.crearPendientesParaSeccion` crea `EvaluacionPendiente` para cada matrícula existente.
3) Estudiante matriculado (`MatriculaServiceImpl`) en sección con instrumento genera pendiente si no existe.
4) Estudiante responde (`CreateEvaluacionUseCase`): valida periodo activo, evita duplicados, guarda respuestas y marca pendiente `COMPLETADA`.
5) Docente consulta su resumen por sección (`GetResumenEvaluacionDocenteUseCase`).
6) Comisión ve avance/tasa respuesta por periodo (`GetMetricasPeriodoUseCase`).

---

## 🗂️ Migraciones clave
- `V10__create_instrumentos_tables.sql` – tablas instrumento/módulo/pregunta.
- `V11__asignar_instrumento_a_seccion.sql` – FK sección → instrumento.
- `V12__create_evaluaciones_pendientes.sql` – pendientes (matricula, sección, instrumento, estado).
- `V5__create_periodos_table.sql`, `V7__seed_cursos.sql`, `V8__align_entities_tables.sql` etc. para base académica.

---

## 📊 Relación con el programa de curso
- Unidad I (Fundamentos/estrategias): principios SOLID, acoplamiento/abstracción (aplicados en casos de uso y repositorios).
- Unidad II (Diseño de datos): persistencia de evaluaciones/instrumentos/matrículas con JPA y migraciones.
- Unidad III (Diseño de UI/UX): expone endpoints para formularios dinámicos (instrumento → preguntas).
- Unidad IV (Patrones/arquitectura): modularización por casos de uso, repositorios, DTOs; separación de responsabilidades y validaciones.

---

## 🔧 Scripts
- `mvn clean` – limpiar
- `mvn test` – pruebas
- `mvn -DskipTests compile` – compilar rápido

---
