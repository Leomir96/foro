-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    login varchar(100) not null,
    password varchar(300) not null,
    primary key(ID)
);

-- Crear tabla de cursos
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Crear tabla de tópicos
CREATE TABLE IF NOT EXISTS topics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    author_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Insertar datos iniciales en la tabla de usuarios
INSERT INTO users (name, email) VALUES
('Juan Pérez', 'juan.perez@example.com'),
('Ana García', 'ana.garcia@example.com');

-- Insertar datos iniciales en la tabla de cursos
INSERT INTO courses (name, description) VALUES
('Introducción a Spring Boot', 'Curso básico sobre Spring Boot'),
('Bases de datos con MySQL', 'Aprende a manejar bases de datos relacionales con MySQL');
