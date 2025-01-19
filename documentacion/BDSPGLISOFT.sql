CREATE SCHEMA IF NOT EXISTS spglisoft;
USE spglisoft;

CREATE TABLE IF NOT EXISTS usuario(
	idUsuario INT NOT NULL AUTO_INCREMENT,
    nombre 	VARCHAR (25),
    apellidoPaterno VARCHAR (25),
    apellidoMaterno VARCHAR (25),
    correo VARCHAR (50),
    contrasenia VARCHAR(25),
    PRIMARY KEY (idUsuario)
);

CREATE TABLE IF NOT EXISTS responsableProyecto(
	idResponsableProyecto INT NOT NULL,
    numeroPersonal VARCHAR(10),
    idProyecto INT,
    PRIMARY KEY (idResponsableProyecto)
);

CREATE TABLE IF NOT EXISTS experienciaEducativa(
	idExperienciaEducativa INT NOT NULL AUTO_INCREMENT,
    NRC VARCHAR(10),
    idResponsableProyecto INT,
    idPeriodoEscolar INT,
	idMateria INT,
    PRIMARY KEY (idExperienciaEducativa)
);

CREATE TABLE IF NOT EXISTS periodoEscolar(
	idPeriodoEscolar INT NOT NULL AUTO_INCREMENT,
	fechaInicio DATE,
    fechaFin DATE,
    PRIMARY KEY (idPeriodoEscolar)
);

CREATE TABLE IF NOT EXISTS materia(
	idMateria INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (50),
    PRIMARY KEY (idMateria)
);

CREATE TABLE IF NOT EXISTS inscripcionExperienciaEducativa(
	idInscripcion INT NOT NULL AUTO_INCREMENT,
    idExperienciaEducativa INT,
    idDesarrollador INT,
    PRIMARY KEY (idInscripcion)
);

CREATE TABLE IF NOT EXISTS desarrollador(
	idDesarrollador INT NOT NULL,
    matricula VARCHAR (20),
    idProyecto INT,
    PRIMARY KEY (idDesarrollador)
);

CREATE TABLE IF NOT EXISTS proyecto(
	idProyecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (100),
    fechaInicio DATE,
    fechaFin DATE,
    descripcion LONGTEXT,
    numeroDesarrolladoresMaximo INT,
    idEstadoProyecto INT,
    PRIMARY KEY (idProyecto)
);

CREATE TABLE IF NOT EXISTS estadoProyecto(
	idEstadoProyecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idEstadoProyecto)
);

CREATE TABLE IF NOT EXISTS actividad(
	idActividad INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (100),
    fechaInicio DATE,
    fechaFin DATE,
    descripcion LONGTEXT,
    esfuerzo INT,
    idProyecto INT,
    idDesarrollador INT,
    idEstadoActividad INT,
    PRIMARY KEY (idActividad)
);

CREATE TABLE IF NOT EXISTS estadoCambio(
	idEstadoCambio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idEstadoCambio)
);

CREATE TABLE IF NOT EXISTS estadoActividad(
	idEstadoActividad INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idEstadoActividad)
);

CREATE TABLE IF NOT EXISTS defecto(
	idDefecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (100),
    fechaDeteccion DATE,
    descripcion LONGTEXT,
    esfuerzo INT,
    idProyecto INT,
    idDesarrollador INT,
    idEstadoDefecto INT,
    idTipoDefecto INT,
    PRIMARY KEY (idDefecto)
);

CREATE TABLE IF NOT EXISTS estadoDefecto(
	idEstadoDefecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idEstadoDefecto)
);

CREATE TABLE IF NOT EXISTS tipoDefecto(
	idTipoDefecto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idTipoDefecto)
);

CREATE TABLE IF NOT EXISTS solicitudCambio(
	idSolicitudCambio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (100),
    fechaSolicitud DATE,
    fechaAprobacion DATE,
    descripcion LONGTEXT,
    razon LONGTEXT,
    impacto LONGTEXT,
    accionPropuesta LONGTEXT,
    idProyecto INT,
    idDesarrollador INT,
    idDefecto INT NULL,
    idEstadoSolicitud INT,
    PRIMARY KEY (idSolicitudCambio),
    FOREIGN KEY (idDefecto) REFERENCES defecto(idDefecto)
);

CREATE TABLE IF NOT EXISTS cambio(
	idCambio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (100),
    fechaCreacion DATE,
    descripcion LONGTEXT,
    esfuerzo INT,
    idProyecto INT,
    idDesarrollador INT,
    idSolicitudCambio INT NULL,
    idTipoDefecto INT,
    idEstadoCambio INT,
    PRIMARY KEY (idCambio),
    FOREIGN KEY (idSolicitudCambio) REFERENCES solicitudCambio(idSolicitudCambio)
);

CREATE TABLE IF NOT EXISTS estadoSolicitud(
	idEstadoSolicitud INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR (20),
    PRIMARY KEY (idEstadoSolicitud)
);

ALTER TABLE experienciaEducativa ADD CONSTRAINT FK_experienciaEducativa_periodoEscolar
FOREIGN KEY (idPeriodoEscolar)
REFERENCES periodoEscolar(idPeriodoEscolar) ON DELETE CASCADE;

ALTER TABLE experienciaEducativa ADD CONSTRAINT FK_experienciaEducativa_responsableProyecto
FOREIGN KEY (idResponsableProyecto)
REFERENCES responsableProyecto(idResponsableProyecto) ON DELETE CASCADE;

ALTER TABLE experienciaEducativa ADD CONSTRAINT FK_experienciaEducativa_materia
FOREIGN KEY (idMateria)
REFERENCES materia(idMateria) ON DELETE CASCADE;

ALTER TABLE inscripcionExperienciaEducativa ADD CONSTRAINT FK_inscripcionExperienciaEducativa_ExperienciaEducativa
FOREIGN KEY (idExperienciaEducativa)
REFERENCES experienciaEducativa(idExperienciaEducativa) ON DELETE CASCADE;

ALTER TABLE inscripcionExperienciaEducativa ADD CONSTRAINT FK_inscripcionExperienciaEducativa_desarrollador
FOREIGN KEY (idDesarrollador)
REFERENCES desarrollador(idDesarrollador) ON DELETE CASCADE;

ALTER TABLE desarrollador ADD CONSTRAINT FK_desarrollador_proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE responsableProyecto ADD CONSTRAINT FK_responsableProyecto_proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE proyecto ADD CONSTRAINT FK_proyecto_estadoProyecto
FOREIGN KEY (idEstadoProyecto)
REFERENCES estadoProyecto(idEstadoProyecto) ON DELETE CASCADE;

ALTER TABLE actividad ADD CONSTRAINT FK_actividad_proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE actividad ADD CONSTRAINT FK_actividad_Desarrollador
FOREIGN KEY (idDesarrollador)
REFERENCES desarrollador(idDesarrollador) ON DELETE CASCADE;

ALTER TABLE actividad ADD CONSTRAINT FK_actividad_estadoActividad
FOREIGN KEY (idEstadoActividad)
REFERENCES estadoActividad(idEstadoActividad) ON DELETE CASCADE;

ALTER TABLE cambio ADD CONSTRAINT FK_cambio_proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE cambio ADD CONSTRAINT FK_cambio_solicitudCambio
FOREIGN KEY (idSolicitudCambio)
REFERENCES solicitudCambio(idSolicitudCambio) ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE cambio ADD CONSTRAINT FK_cambio_tipoDefecto
FOREIGN KEY (idTipoDefecto)
REFERENCES tipoDefecto(idTipoDefecto) ON DELETE CASCADE;
ALTER TABLE cambio ADD CONSTRAINT FK_cambio_estadoCambio
FOREIGN KEY (idEstadoCambio)
REFERENCES estadoCambio(idEstadoCambio) ON DELETE CASCADE;

ALTER TABLE responsableProyecto ADD CONSTRAINT FK_responsableProyecto_usuario
FOREIGN KEY (idResponsableProyecto)
REFERENCES usuario(idUsuario) ON DELETE CASCADE;

ALTER TABLE desarrollador ADD CONSTRAINT FK_desarrollador_usuario
FOREIGN KEY (idDesarrollador)
REFERENCES usuario(idUsuario) ON DELETE CASCADE;

ALTER TABLE defecto ADD CONSTRAINT FK_defecto_proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE defecto ADD CONSTRAINT FK_defecto_Desarrollador
FOREIGN KEY (idDesarrollador)
REFERENCES desarrollador(idDesarrollador) ON DELETE CASCADE;

ALTER TABLE defecto ADD CONSTRAINT FK_defecto_estadoDefecto
FOREIGN KEY (idEstadoDefecto)
REFERENCES estadoDefecto(idEstadoDefecto) ON DELETE CASCADE;

ALTER TABLE defecto ADD CONSTRAINT FK_defecto_tipoDefecto
FOREIGN KEY (idTipoDefecto)
REFERENCES tipoDefecto(idTipoDefecto) ON DELETE CASCADE;

ALTER TABLE solicitudCambio ADD CONSTRAINT FK_solicitudCambio_Desarrollador
FOREIGN KEY (idDesarrollador)
REFERENCES desarrollador(idDesarrollador) ON DELETE CASCADE;

ALTER TABLE solicitudCambio ADD CONSTRAINT FK_solicitudCambio_Proyecto
FOREIGN KEY (idProyecto)
REFERENCES proyecto(idProyecto) ON DELETE CASCADE;

ALTER TABLE solicitudCambio ADD CONSTRAINT FK_solicitudCambio_Defecto
FOREIGN KEY (idDefecto)
REFERENCES defecto(idDefecto) ON DELETE  NO ACTION;

ALTER TABLE solicitudCambio ADD CONSTRAINT FK_solicitudCambio_estadoSolicitud
FOREIGN KEY (idEstadoSolicitud)
REFERENCES estadoSolicitud(idEstadoSolicitud) ON DELETE CASCADE;


SET FOREIGN_KEY_CHECKS=0;
INSERT INTO spglisoft.cambio (idCambio, nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador, idSolicitudCambio, idTipoDefecto, idEstadoCambio) VALUES ('1', 'Inconsistencia de datos', '2022-08-20', 'Hay atributos no representativos para tabla productos', '120', '1', '1', '0', '1', '2');
INSERT INTO spglisoft.cambio (idCambio, nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador, idSolicitudCambio, idTipoDefecto, idEstadoCambio) VALUES ('2', 'Falta de normalización', '2022-11-05', 'Se requiere que se modelen catalogos para la base de datos', '210', '1', '1', '0', '1', '2');
INSERT INTO spglisoft.cambio (idCambio, nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador, idSolicitudCambio, idTipoDefecto, idEstadoCambio) VALUES ('3', 'Pase de parametros erroneo', '2022-10-17', 'Se pierde la informacion del menu principal al menu de productos', '150', '1', '2', '0', '3', '2');
INSERT INTO spglisoft.cambio (idCambio, nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador, idSolicitudCambio, idTipoDefecto, idEstadoCambio) VALUES ('4', 'Tabla medidas no se usa', '2022-12-30', 'La medida ya se incluye en las etiquetas, remover', '415', '1', '2', '0', '1', '2');
INSERT INTO spglisoft.cambio (idCambio, nombre, fechaCreacion, descripcion, esfuerzo, idProyecto, idDesarrollador, idSolicitudCambio, idTipoDefecto, idEstadoCambio) VALUES ('5', 'Registros duplicados', '2022-09-08', 'Agregar validacion de registros duplicados', '60', '1', '2', '0', '3', '2');
SET FOREIGN_KEY_CHECKS=1;




-- ----------------------------Para los INSERT-------------------------------
INSERT INTO periodoescolar(idPeriodoEscolar, fechaInicio, fechaFin) VALUES ('1', '2023-08-18', '2024-01-10');

INSERT INTO materia(idMateria, nombre) VALUES ('1', 'Practicas Profesionales'), ('2', 'Servicio Social');

INSERT INTO estadoproyecto(idEstadoProyecto, nombre) VALUES (1, "Creada"), (2, "En curso"), (3, 'Finalizada');

INSERT INTO proyecto(idProyecto, nombre, fechaInicio, fechaFin, descripcion, numeroDesarrolladoresMaximo, idEstadoProyecto) VALUES ('1', 'Proyecto para las Practicas Profesionales', '2023-09-18', '2024-01-01', 'Proyecto encargado de cubrir la Experiencia Educativa de Practicas Profesionales', '2', '2'), ('2', 'Proyecto para el Servicio Social', '2023-09-18', '2024-01-01', 'Proyecto encargado de cubrir la Experiencia Educativa del Servicio Social', '2', '1');

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('Miguel', 'Martinez', 'Caixba','s21013850@uv.mx', 'caixba123');

INSERT INTO desarrollador(idDesarrollador, matricula, idProyecto) VALUES (LAST_INSERT_ID(),'S21013850', 1);

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('Daniel', 'Mongeote', 'Tlachy','s21013851@uv.mx', 'tlachy123');

INSERT INTO desarrollador(idDesarrollador, matricula, idProyecto) VALUES (LAST_INSERT_ID(),'S21013851', 1);

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('Alesis', 'Torres', 'Osorio','s21013852@uv.mx', 'osorio123');

INSERT INTO desarrollador(idDesarrollador, matricula, idProyecto) VALUES (LAST_INSERT_ID(),'S21013852', 1);

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('Octavio', 'Ocharan', 'Hernandez','s21000001@uv.mx', 'ocharan123');

INSERT INTO responsableproyecto(idResponsableProyecto, numeroPersonal, idProyecto) VALUES (LAST_INSERT_ID(),'12345', 1);

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('Maria Karen', 'Cortes', 'Verdin','s21000002@uv.mx', 'karen123');

INSERT INTO responsableproyecto(idResponsableProyecto, numeroPersonal, idProyecto) VALUES (LAST_INSERT_ID(),'54321', 2);

INSERT INTO experienciaeducativa(idExperienciaEducativa, NRC, idResponsableProyecto, idPeriodoEscolar, idMateria) VALUES ('1', '78978', '4', '1', '1'), ('2', '87954', '5', '1', '2');

INSERT INTO inscripcionexperienciaeducativa(idInscripcion, idExperienciaEducativa, idDesarrollador) VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3');

INSERT INTO usuario(idUsuario, nombre, apellidoPaterno, apellidoMaterno, correo, contrasenia) VALUES ('6', 'Ramon', 'Gomez', 'Romero', 's2100003@uv.mx', 'ramon123'), ('7', 'Itzel', 'Reyes', 'Lopez', 's2100004@uv.mx', 'itzel123');

INSERT INTO responsableproyecto(idResponsableProyecto, numeroPersonal) VALUES ('6', '00000'), ('7', '11111');

INSERT INTO experienciaeducativa(idExperienciaEducativa, NRC, idResponsableProyecto, idPeriodoEscolar, idMateria) VALUES ('3', '11111', '6', '1', '1');

INSERT INTO estadoactividad(idEstadoActividad, nombre) VALUES (1, "Sin asignar"), (2, "Asignada"), (3, 'Finalizada');

INSERT INTO actividad(idActividad, nombre, fechaInicio, fechaFin, descripcion, esfuerzo, idProyecto, idDesarrollador,  idEstadoActividad) VALUES
	(1, 'Cambiar tabla desarrollador en la base de datos', '2023-10-11', '2023-10-15', 'La columna domicilio debe ser agregada', 20, 1, 1, 2),
    (2, 'Eliminar tabla ayudante en la base de datos', '2023-10-13', '2023-10-16', 'La tabla ya no es util en el sistema', 30, 1, 2, 2),
    (3, 'Agregar edad al Pojo Desarrollador', '2023-10-14', '2023-10-17', 'Se debe agregar ese atributo porque no fue contemplado', 40, 1, 3, 2),
    (4, 'Cambiar tabla responsable en la base de datos', '2023-10-15', '2023-10-18', 'La longitud de la columna nombre ahora sera 50', 50, 1, 1, 2),
    (5, 'Agregar autenticación de dos factores', '2023-10-16', '2023-10-19', 'Esto implica la integración de métodos biométricos', 60, 1, 2, 2),
    (6, 'Crear API REST para gestión de productos', '2023-10-15', '2023-10-18', 'La longitud de la columna nombre ahora sera 50', 70, 1, 3, 2),
    (7, 'Cambiar tabla responsable en la base de datos', '2023-10-18', '2023-10-21', 'Esto permitirá la creación, lectura, actualización y eliminación de registros de productos en la base de datos', 80, 1, NULL, 1),
    (8, 'Corregir bug en la validación de formularios', '2023-10-19', '2023-10-22', 'Identificar y corregir un error en la lógica de validación de formularios', 90, 1, NULL, 1);

INSERT INTO tipoDefecto(idTipoDefecto, nombre) VALUES (1, 'Base de datos'), (2, 'JavaScript'), (3, 'Codigo'), (4, 'Interfaz');

INSERT INTO  estadoDefecto(idEstadoDefecto, nombre) VALUES (1, 'Pendiente'), (2, 'Resuelto');

INSERT INTO defecto(idDefecto, nombre, fechaDeteccion, descripcion,  esfuerzo, idProyecto, idDesarrollador, idEstadoDefecto, idTipoDefecto) VALUES
	(1, 'Defecto Tabla Insumos', '2021-10-12', 'La tabla de insumos esta mal, arreglenla', '120', 1, 1, 1, 1),
	(2, 'Corregir libreria', '2021-10-13', 'Se deberá de generar un reporte especificando los cambios', '130', 1, 1, 1, 2),
    (3, 'Nombres no descriptivos', '2021-11-06', 'Se deben de usar nombres que revelen intención', '50', 1, 1, 1, 3),
    (4, 'Revisar botón Seleccionar', '2021-10-03', 'El botón arroja alertas', '80', 1, 2, 1, 4);

INSERT INTO estadoSolicitud(idEstadoSolicitud, nombre) VALUES (1, "Pendiente"), (2, "Aprobada"), (3, 'Rechazada');

INSERT INTO solicitudcambio(idSolicitudCambio, nombre, fechaSolicitud, descripcion, razon, impacto, accionPropuesta, idProyecto, idDesarrollador, idDefecto, idEstadoSolicitud) VALUES
	(1, 'Fuente de letra en la descripción del producto no óptima', '2023-10-11', 'El departamento de publicidad solicita tipo de letra Calibri', 'Cambio estratégico para popularizar el producto', 'No tiene mayor impacto que cambiar la fuente de letra', 'Se propone cambiar la fuente actual por Calibri', 1, 1, 1, 1),
    (2, 'Poca longitud en la descripción del producto', '2023-10-15', 'La longitud para ese atributo es demasiado pequeña para toda la información', 'Se tiene que poner una descripción más detallada', 'Solo se tiene que cambiar el tipo de dato del atributo', 'Se debe de cambiar el tipo de dato a LONGTEXT', 1, 2, 1, 1),
    (3, 'Conexión a la Base de datos abierta', '2023-10-16', 'La conexión a la Base de datos se queda abierta', 'La conexión siempre debe de cerrarse después de alguna operación', 'Se deben de checar los demás métodos de la clase', 'Ver ProductoDAO y revisar que todas sus conexiones se cierren', 1, 3, 1, 1);
    
INSERT INTO estadoCambio(idEstadoCambio, nombre) VALUES (1, 'Pendiente'), (2, 'Finalizado');

-- SolcitudCambioDAO.crearSolicitudCambio
DELIMITER //
CREATE PROCEDURE sp_InsertarSolicitudCambio(
    IN p_nombre VARCHAR(100),
    IN p_descripcion LONGTEXT,
    IN p_razon LONGTEXT,
    IN p_impacto LONGTEXT,
    IN p_accionPropuesta LONGTEXT,
    IN p_idDesarrollador INT,
    IN p_idDefecto INT
)
BEGIN
    INSERT INTO solicitudCambio (
        nombre,
        fechaSolicitud,
        descripcion,
        razon,
        impacto,
        accionPropuesta,
        idProyecto,
        idDesarrollador,
        idDefecto,
        idEstadoSolicitud
    )
    VALUES (
        p_nombre,
        CURDATE(),
        p_descripcion,
        p_razon,
        p_impacto,
        p_accionPropuesta,
        (SELECT idProyecto FROM desarrollador WHERE idDesarrollador = p_idDesarrollador),
        p_idDesarrollador,
        p_idDefecto,
        1
    );
END //
DELIMITER ;

-- SolcitudCambioDAO.crearSolicitudCambioSinDefecto
DELIMITER //
CREATE PROCEDURE sp_InsertarSolicitudCambioSinDefecto(
    IN p_nombre VARCHAR(100),
    IN p_descripcion LONGTEXT,
    IN p_razon LONGTEXT,
    IN p_impacto LONGTEXT,
    IN p_accionPropuesta LONGTEXT,
    IN p_idDesarrollador INT
)
BEGIN
    INSERT INTO solicitudCambio (
        nombre,
        fechaSolicitud,
        descripcion,
        razon,
        impacto,
        accionPropuesta,
        idProyecto,
        idDesarrollador,
        idEstadoSolicitud
    )
    VALUES (
        p_nombre,
        CURDATE(),
        p_descripcion,
        p_razon,
        p_impacto,
        p_accionPropuesta,
        (SELECT idProyecto FROM desarrollador WHERE idDesarrollador = p_idDesarrollador),
        p_idDesarrollador,     
        1
    );
END //
DELIMITER ;