SHOW TABLES;

SELECT * FROM usuario;

ALTER TABLE USUARIO ADD COLUMN failedAttempts INTEGER NOT NULL DEFAULT 0;

INSERT INTO PUBLIC.ROL (id, descripcion) VALUES (1, 'ADMINISTRADOR');
INSERT INTO ROL (id, descripcion) VALUES (2, 'REGISTRO_ACADEMICO');
INSERT INTO ROL (id, descripcion) VALUES (3, 'BENEFICIOS ESTUDIANTILES');
INSERT INTO ROL (id, descripcion) VALUES (4, 'POSTULANTE');

INSERT INTO USUARIO (ID, APELLIDOS, CORREO, NOMBRES, PASSWORD, USERNAME)
VALUES (1, 'PEREZ', 'jperez@test.com', 'JUAN', '1234', 'jperez');

INSERT INTO USUARIO (ID, APELLIDOS, CORREO, NOMBRES, PASSWORD, USERNAME)
VALUES (2, 'MONKEY D.', 'lmonkeyd@test.com', 'LUFFY', '1234', 'lmonkeyd');

INSERT INTO USUARIO (ID, APELLIDOS, CORREO, NOMBRES, PASSWORD, USERNAME)
VALUES (3, 'RORONOA', 'zroronoa@test.com', 'ZORO', '1234', 'zroronoa');

INSERT INTO USUARIO (ID, APELLIDOS, CORREO, NOMBRES, PASSWORD, USERNAME)
VALUES (4, 'VINKSMOKE', 'svinksmoke@test.com', 'SANJI', '1234', 'svinksmoke');

INSERT INTO USUARIO_ROL(ID, ROL_ID, USUARIO_ID)
VALUES (1, 1, 1);

INSERT INTO USUARIO_ROL(ID, ROL_ID, USUARIO_ID)
VALUES (2, 2, 2);

INSERT INTO USUARIO_ROL(ID, ROL_ID, USUARIO_ID)
VALUES (3, 3, 3);

INSERT INTO USUARIO_ROL(ID, ROL_ID, USUARIO_ID)
VALUES (4, 4, 4);

INSERT INTO CARRERA(ID, CODIGO, FACULTAD, NOMBRE)
VALUES (1, 1020, 'INGENIERIA', 'INGENIERIA CIVIL BIOMEDICA');

INSERT INTO CARRERA(ID, CODIGO, FACULTAD, NOMBRE)
VALUES (2, 1021, 'HUMANIDADES', 'PEDAGOGIA EN INGLES');

INSERT INTO CARRERA(ID, CODIGO, FACULTAD, NOMBRE)
VALUES (3, 1136, 'ADMINISTRACION Y ECONOMIA', 'ADMINISTRACION PUBLICA');