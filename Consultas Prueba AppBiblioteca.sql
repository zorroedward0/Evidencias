-- para comodidad de pruebas del sistema se adjuntan consultas SQL para cada tabla
-- si desea saber mas informacion leer README.md

USE dbbiblioteca;

-- tiposUsuario
INSERT INTO tipousuario (idtipoUsuario, nombre) VALUES
(1, 'Administrador'),
(2, 'Cliente');

-- usuarios
INSERT INTO usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario, password) VALUES
('1001','Juan','Perez','juan1@mail.com','300000001',1,1,'123'),
('1002','Ana','Gomez','ana@mail.com','300000002',1,2,'123'),
('1003','Luis','Martinez','luis@mail.com','300000003',1,2,'123'),
('1004','Sofia','Lopez','sofia@mail.com','300000004',1,2,'123'),
('1005','Carlos','Ruiz','carlos@mail.com','300000005',1,2,'123'),
('1006','Maria','Torres','maria@mail.com','300000006',1,2,'123'),
('1007','Pedro','Diaz','pedro@mail.com','300000007',1,2,'123'),
('1008','Laura','Castro','laura@mail.com','300000008',1,2,'123'),
('1009','Jorge','Vargas','jorge@mail.com','300000009',1,2,'123'),
('1010','Elena','Rojas','elena@mail.com','300000010',1,2,'123');

-- autores
INSERT INTO autor (nombre, apellido, nacionalidad, fechaNacimiento) VALUES
('Gabriel','Garcia Marquez','Colombiano','1927-03-06'),
('Mario','Vargas Llosa','Peruano','1936-03-28'),
('Julio','Cortazar','Argentino','1914-08-26'),
('Isabel','Allende','Chilena','1942-08-02'),
('Jorge Luis','Borges','Argentino','1899-08-24'),
('Pablo','Neruda','Chileno','1904-07-12'),
('Miguel','de Cervantes','Español','1547-09-29'),
('William','Shakespeare','Ingles','1564-04-26'),
('Ernest','Hemingway','Estadounidense','1899-07-21'),
('Jane','Austen','Inglesa','1775-12-16');

-- editoriales
INSERT INTO editorial (nombre, pais, sitioWeb) VALUES
('Planeta','España','www.planeta.com'),
('Norma','Colombia','www.norma.com'),
('Santillana','España','www.santillana.com'),
('Penguin','USA','www.penguin.com'),
('HarperCollins','USA','www.harper.com'),
('Oxford','UK','www.oxford.com'),
('Cambridge','UK','www.cambridge.com'),
('Alfaguara','España','www.alfaguara.com'),
('Anagrama','España','www.anagrama.com'),
('Debolsillo','España','www.debolsillo.com');

-- categorias
INSERT INTO categoria (nombre, descripcion) VALUES
('Novela','Narrativa extensa'),
('Cuento','Narrativa corta'),
('Poesia','Genero poetico'),
('Drama','Genero teatral'),
('Historia','Libros historicos'),
('Ciencia','Libros cientificos'),
('Fantasia','Mundo fantastico'),
('Terror','Genero de miedo'),
('Romance','Historias romanticas'),
('Aventura','Relatos de aventura');

-- Libros
INSERT INTO libro (titulo, isbn, añoPublicacion, numPag, disponible, idEditorial) VALUES
('Cien años de soledad','ISBN001',1967,417,1,1),
('La ciudad y los perros','ISBN002',1963,300,1,2),
('Rayuela','ISBN003',1963,600,1,3),
('La casa de los espiritus','ISBN004',1982,450,1,4),
('Ficciones','ISBN005',1944,200,1,5),
('Veinte poemas de amor','ISBN006',1924,150,1,6),
('Don Quijote','ISBN007',1605,1000,1,7),
('Hamlet','ISBN008',1603,250,1,8),
('El viejo y el mar','ISBN009',1952,120,1,9),
('Orgullo y prejuicio','ISBN010',1813,300,1,10);

-- Libro Autor
INSERT INTO libroautor (idAutor, idLibro) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10);

-- Libro Categoria
INSERT INTO librocategoria (idLibro, idCategoria) VALUES
(1,1),
(2,1),
(3,1),
(4,1),
(5,2),
(6,3),
(7,1),
(8,4),
(9,2),
(10,9);

 -- Prestamos
INSERT INTO prestamo (fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionReal, estado, idLibro, idUsuario) VALUES
('2026-01-01','2026-01-10','2026-01-09',1,1,1),
('2026-01-02','2026-01-12',NULL,0,2,2),
('2026-01-03','2026-01-13',NULL,0,3,3),
('2026-01-04','2026-01-14','2026-01-14',1,4,4),
('2026-01-05','2026-01-15',NULL,0,5,5),
('2026-01-06','2026-01-16',NULL,0,6,6),
('2026-01-07','2026-01-17','2026-01-16',1,7,7),
('2026-01-08','2026-01-18',NULL,0,8,8),
('2026-01-09','2026-01-19',NULL,0,9,9),
('2026-01-10','2026-01-20','2026-01-19',1,10,10);