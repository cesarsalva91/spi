-- Crear base de datos
CREATE DATABASE IF NOT EXISTS classroommanagerdb;

-- Usar la base de datos
USE classroommanagerdb;

-- Crear la tabla Estudiante
CREATE TABLE Estudiante (
    id_estudiante INT PRIMARY KEY AUTO_INCREMENT,  
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    matricula INT NOT NULL, 
    contacto VARCHAR(100) 
);

-- Crear la tabla Asistencia
CREATE TABLE Asistencia (
    id_asistencia INT PRIMARY KEY AUTO_INCREMENT,  
    fecha DATE NOT NULL,  
    estado VARCHAR(20) NOT NULL,  
    justificada BOOLEAN NOT NULL,  
    id_estudiante INT,  
    FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante)
);

-- Crear la tabla Materia
CREATE TABLE Materia (
    id_materia INT PRIMARY KEY AUTO_INCREMENT,  
    nombre VARCHAR(100) NOT NULL,  
    descripcion TEXT
);

-- Crear la tabla Calificacion
CREATE TABLE Calificacion (
    id_calificacion INT PRIMARY KEY AUTO_INCREMENT,  
    nota FLOAT NOT NULL,  
    id_estudiante INT,  
    id_materia INT,  
    FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante), 
    FOREIGN KEY (id_materia) REFERENCES Materia(id_materia)  
);

-- Crear la tabla Notificacion
CREATE TABLE Notificacion (
    id_notificacion INT PRIMARY KEY AUTO_INCREMENT,  
    mensaje TEXT NOT NULL,  
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    id_estudiante INT,  
    FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante)  
);