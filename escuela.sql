DROP DATABASE IF EXISTS escuela;
CREATE DATABASE IF NOT EXISTS escuela;
USE escuela;

CREATE TABLE categoria
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(20) UNIQUE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO categoria(nombre) VALUES ('Alumno');
INSERT INTO categoria(nombre) VALUES ('Profesor');

CREATE TABLE usuario
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    nick VARCHAR(20) UNIQUE NOT NULL,
    pass VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id INT UNSIGNED NOT NULL,
    FOREIGN KEY(categoria_id) REFERENCES categoria(id)
);

INSERT INTO usuario(nombre, nick, pass, categoria_id) VALUES
('Fabi SuperFabulosa', 'fabi', 'CrYmvqLct2telyyAAFgeIz8oRAM+0hn06V5FS66DNiU=', 1);
INSERT INTO usuario(nombre, nick, pass, categoria_id) VALUES
('Adii La Chida', 'adi', '3PRWcvStNdEEEAbLS5nc/gil3AMWJnY9tKD0CF1yQfI=', 2);
INSERT INTO usuario(nombre, nick, pass, categoria_id) VALUES
('Oscar El de los Churros', 'oscarin', 'wYPTLDLVO1l2yxTUlu6aKzCgWrvlX4HjJgNnB0H14Dg=', 1);
INSERT INTO usuario(nombre, nick, pass, categoria_id) VALUES
('Mateo El Nuevo', 'mateo', 'KH2kS+bdBg2ro/GzqThaOSCapMuuYFNYEz09EYZa8dM=', 1);
INSERT INTO usuario(nombre, nick, pass, categoria_id) VALUES
('Javier El Enfadoso', 'javi', 'cnUCrql82JrEM6EH45T9s9bNT53gRWBzDpSQdW/lcPQ=', 2);

CREATE TABLE materia
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    clave VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO materia(clave, nombre) VALUES('ADR01', 'Android');
INSERT INTO materia(clave, nombre) VALUES('JVA02', 'Java');
INSERT INTO materia(clave, nombre) VALUES('C++03', 'C++');
INSERT INTO materia(clave, nombre) VALUES('PHP04', 'PHP');

CREATE TABLE evaluacion
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    calificacion DOUBLE NOT NULL CHECK(calificacion > 0 AND calificacion <= 10),
    fecha DATETIME NOT NULL DEFAULT now(),
    usuario_id INT UNSIGNED NOT NULL,
    materia_id INT UNSIGNED NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    FOREIGN KEY(materia_id) REFERENCES materia(id)
);

INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (8, 1, 2);
INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (10, 3, 1);
INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (7, 4, 4);
INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (5, 1, 3);
INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (9, 3, 3);
INSERT INTO evaluacion(calificacion, usuario_id, materia_id)
VALUES (8, 3, 2);

-- http://atc.mx/android/atcproject/library/seguridad/encriptar.php?cadena=fab1