use dbbiblioteca;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE multa;
TRUNCATE prestamo;
TRUNCATE libroautor;
TRUNCATE librocategoria;
TRUNCATE libro;
TRUNCATE autor;
TRUNCATE categoria;
TRUNCATE editorial;
TRUNCATE usuario;
TRUNCATE tipousuario;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO editorial (nombre,pais,sitioWeb) VALUES
('Planeta','España','https://www.planetadelibros.com'),
('Penguin Random House','USA','https://www.penguinrandomhouse.com'),
('Alfaguara','España','https://www.alfaguara.com'),
('Editorial Norma','Colombia','https://www.norma.com'),
('Fondo de Cultura Económica','México','https://www.fce.com.mx');

INSERT INTO autor (nombre,apellido,nacionalidad,fechaNacimiento) VALUES
('Gabriel','Garcia Marquez','Colombiano','1927-03-06'),
('Laura','Restrepo','Colombiana','1950-01-01'),
('Hector','Abad Faciolince','Colombiano','1958-10-01'),
('William','Ospina','Colombiano','1954-03-02'),
('Jorge Luis','Borges','Argentino','1899-08-24'),
('Isabel','Allende','Chilena','1942-08-02'),
('Julio','Cortazar','Argentino','1914-08-26'),
('Stephen','King','Estadounidense','1947-09-21'),
('George','Orwell','Britanico','1903-06-25'),
('J.K.','Rowling','Britanica','1965-07-31');

INSERT INTO categoria (nombre,descripcion) VALUES
('Novela','Narrativa'),
('Realismo Magico','Literatura latinoamericana'),
('Historia','Historia y politica'),
('Fantasia','Mundos fantasticos'),
('Terror','Suspenso y miedo'),
('Drama','Historias humanas'),
('Clasicos','Obras clasicas'),
('Juvenil','Lectura juvenil'),
('Ciencia Ficcion','Futuro y tecnologia'),
('Biografia','Historias reales');


INSERT INTO libro (titulo,isbn,añoPublicacion,numPag,disponible,idEditorial,destacado,imagen) VALUES
('Cien años de soledad','ISBN001',1967,417,1,1,1,'1.jpg'),
('El amor en los tiempos del colera','ISBN002',1985,348,1,1,0,'2.jpg'),
('Cronica de una muerte anunciada','ISBN003',1981,122,1,1,0,'3.jpg'),
('La hojarasca','ISBN004',1955,180,1,1,0,'4.jpg'),
('Delirio','ISBN005',2004,330,1,3,0,'5.jpg'),
('La isla de la pasion','ISBN006',1989,280,1,3,0,'6.jpg'),
('El olvido que seremos','ISBN007',2006,350,1,4,1,'7.jpg'),
('Angosta','ISBN008',2003,450,1,4,0,'8.jpg'),
('El pais de la canela','ISBN009',2008,320,1,5,0,'9.jpg'),
('La serpiente sin ojos','ISBN010',2012,300,1,5,0,'10.jpg'),

('Ficciones','ISBN011',1944,200,1,2,0,'11.jpg'),
('El Aleph','ISBN012',1949,190,1,2,0,'12.jpg'),
('La casa de los espiritus','ISBN013',1982,450,1,3,0,'13.jpg'),
('Rayuela','ISBN014',1963,600,1,2,0,'14.jpg'),
('It','ISBN015',1986,1138,1,2,0,'15.jpg'),
('Carrie','ISBN016',1974,250,1,2,0,'16.jpg'),
('1984','ISBN017',1949,328,1,2,1,'17.jpg'),
('Rebelion en la granja','ISBN018',1945,144,1,2,0,'18.jpg'),
('Harry Potter y la piedra filosofal','ISBN019',1997,320,1,2,1,'19.jpg'),
('Harry Potter y la camara secreta','ISBN020',1998,341,1,2,0,'20.jpg'),

('El coronel no tiene quien le escriba','ISBN021',1961,120,1,1,0,'21.jpg'),
('Noticia de un secuestro','ISBN022',1996,320,1,1,0,'22.jpg'),
('Historia secreta de Costaguana','ISBN023',2007,400,1,5,0,'23.jpg'),
('La tejedora de coronas','ISBN024',1982,500,1,5,0,'24.jpg'),
('Los divinos','ISBN025',2018,280,1,3,0,'25.jpg'),
('La forma de las ruinas','ISBN026',2015,450,1,3,0,'26.jpg'),
('El ruido de las cosas al caer','ISBN027',2011,260,1,3,0,'27.jpg'),
('Satanas','ISBN028',2002,300,1,4,0,'28.jpg'),
('La sombra del viento','ISBN029',2001,565,1,1,0,'29.jpg'),
('El codigo Da Vinci','ISBN030',2003,450,1,2,0,'30.jpg');

INSERT INTO libroautor VALUES
(1,1),(1,2),(1,3),(1,4),(2,5),(2,6),(3,7),(4,8),(4,9),(4,10),
(5,11),(5,12),(6,13),(7,14),(8,15),(8,16),(9,17),(9,18),(10,19),(10,20);

INSERT INTO librocategoria VALUES
(1,2),(2,1),(3,1),(4,1),(5,6),(6,6),(7,10),(8,1),(9,3),(10,3),
(11,7),(12,7),(13,1),(14,1),(15,5),(16,5),(17,7),(18,7),(19,4),(20,4),
(21,2),(22,3),(23,1),(24,1),(25,6),(26,3),(27,1),(28,5),(29,1),(30,9);

INSERT INTO tipousuario (nombre) VALUES ('Administrador'),('Cliente');

INSERT INTO usuario (documento,nombre,apellido,email,telefono,estado,idTipoUsuario,password) VALUES
('1010','Carlos','Rodriguez','carlos@gmail.com','3001111111',1,1,'1234'),
('1011','Ana','Martinez','ana@gmail.com','3001111112',1,2,'1234'),
('1012','Luis','Gomez','luis@gmail.com','3001111113',1,2,'1234'),
('1013','Maria','Lopez','maria@gmail.com','3001111114',1,2,'1234'),
('1014','Jorge','Castro','jorge@gmail.com','3001111115',1,2,'1234'),
('1015','Sofia','Ramirez','sofia@gmail.com','3001111116',1,2,'1234'),
('1016','Andres','Torres','andres@gmail.com','3001111117',1,2,'1234'),
('1017','Valentina','Morales','vale@gmail.com','3001111118',1,2,'1234'),
('1018','Camilo','Vargas','camilo@gmail.com','3001111119',1,2,'1234'),
('1019','Daniela','Rojas','daniela@gmail.com','3001111120',1,2,'1234');

UPDATE usuario SET password = '123' WHERE password = '1234';


INSERT INTO prestamo (fechaPrestamo,fechaDevolucionEsperada,fechaDevolucionReal,estado,idLibro,idUsuario) VALUES
('2026-01-01','2026-01-10',NULL,0,1,2),
('2026-02-01','2026-02-10','2026-02-09',1,2,3),
('2026-03-01','2026-03-10',NULL,0,3,4),
('2026-03-20','2026-04-10',NULL,0,4,5),
('2026-04-01','2026-04-20',NULL,0,5,6);





