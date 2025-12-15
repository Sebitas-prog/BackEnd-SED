-- Cursos predeterminados (subset basado en imagen de referencia)
INSERT INTO cursos (id, nombre, codigo, facultad, creditos, activo, creado_en)
VALUES
  (gen_random_uuid(), 'Taller de Habilidades Blandas', 'ESH1218A', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Sostenibilidad y Responsabilidad Social', 'ESM1213B', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Redaccion y Comunicacion Efectiva', 'ESZ1211A', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Fundamentos de Computacion', 'ESM1128A', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Futbol', 'LEXP0105', 'FISI', 1, true, now()),
  (gen_random_uuid(), 'Matematica Basica', 'ESM4213C', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Programacion Basica', 'ESZ4216A', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Ingles Tecnico para Informatica', 'ESZ4216B', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Logica', 'ESM4217A', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Circuitos y Ciudadania', 'LEXP0103', 'FISI', 2, true, now()),
  (gen_random_uuid(), 'Pensamiento Sistemico', 'ESM5215B', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Diseno de Base de Datos', 'ESZ5216B', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Construccion de Software I', 'ESZ6216B', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Sistemas Operativos I', 'ESZ6216A', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Gestion de Procesos de Negocio', 'ESM7214B', 'FISI', 3, true, now()),
  (gen_random_uuid(), 'Analitica de Datos', 'ESZ7216B', 'FISI', 4, true, now()),
  (gen_random_uuid(), 'Arquitectura de Software', 'ESZ8216B', 'FISI', 4, true, now());
