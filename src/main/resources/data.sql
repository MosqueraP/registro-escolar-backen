-- Personas base
INSERT INTO persona (nombre, apellido, fecha_nacimiento, email, telefono)
VALUES ('Carlos', 'Ríos', '1980-01-15', 'carlos.rios@correo.com', '3001111111');

INSERT INTO persona (nombre, apellido, fecha_nacimiento, email, telefono)
VALUES ('Juan', 'Gómez', '2006-08-21', 'juan.gomez@correo.com', '3004444444');

INSERT INTO persona (nombre, apellido, fecha_nacimiento, email, telefono)
VALUES ('Andrea', 'López', '1985-03-10', 'andrea.lopez@correo.com', '3002222222');

INSERT INTO persona (nombre, apellido, fecha_nacimiento, email, telefono)
VALUES ('Laura', 'Martínez', '2005-06-12', 'laura.martinez@correo.com', '3003333333');


-- Profesores (referencia a persona)

INSERT INTO profesor (id_persona, especialidad, fecha_contratacion)
VALUES (1, 'Historia', '2020-01-01');

INSERT INTO profesor (id_persona, especialidad, fecha_contratacion)
VALUES (2, 'Matemáticas', '2021-02-01');


-- Estudiantes (referencia a persona)

INSERT INTO estudiante (id_persona, numero_matricula, grado)
VALUES (3, 'EST-2025-001', '10A');

INSERT INTO estudiante (id_persona, numero_matricula, grado)
VALUES (4, 'EST-2025-002', '10B');

-- Cursos (relacionados con profesores)

INSERT INTO curso (nombre, descripcion, creditos, id_profesor)
VALUES ('Historia de Colombia', 'Curso sobre el proceso histórico de Colombia.', 3, 16);

INSERT INTO curso (nombre, descripcion, creditos, id_profesor)
VALUES ('Álgebra Lineal', 'Curso introductorio al álgebra lineal.', 4, 2);


-- Administrativos
INSERT INTO administrativo (id_persona, cargo, departamento) VALUES
 (1, 'Admisiones Académico', 'Admisiones');

INSERT INTO administrativo (id_persona, cargo, departamento) VALUES
 (3, 'Instructor Académico', 'Instructor');

INSERT INTO administrativo (id_persona, cargo, departamento) VALUES
  (2, 'Secretario Académico', 'Secretario');


-- Inscripciones
INSERT INTO inscripcion (id_inscripcion, estudiante_id, curso_id, fecha_inscripcion) VALUES
(1, 4, 2, '2024-02-01'),
INSERT INTO inscripcion (id_inscripcion, estudiante_id, curso_id, fecha_inscripcion) VALUES
(1, 3, 2, '2024-02-01'),

