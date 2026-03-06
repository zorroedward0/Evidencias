-- Consultas
insert into autor (nombre,apellido,nacionalidad,fechaNacimiento) values ('Gabriel','Garcia Marquez','Colombiano','2000-02-02');
select a.nombre as nombres,a.apellido as apellidos from autor as a;
update autor set nacionalidad = 'Mexicano' where nombre = 'Gabriel';
select a.nacionalidad from autor as a where a.fechaNacimiento = '2000-02-02';
delete from autor as a where (a.nombre = 'Gabriel');

insert into tipousuario (nombre) values ('Cliente');
insert into tipousuario (nombre) values ('Administrador');
insert into usuario (documento,nombre,apellido,email,telefono,estado,idTipoUsuario)
values ('10554879564','Jose','Alvarez','JoseAlvares34@gmail.com','3134556879',1,1);
insert into usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario)
values ('1023456789', 'Laura', 'Martinez', 'laura.martinez89@gmail.com', '3205567788', 1,1);
insert into usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario)
values ('1098765432', 'Camilo', 'Rodriguez', 'camilo.rodriguez23@gmail.com', '3114456677', 1, 2);
INSERT INTO usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario)
VALUES ('1002345678', 'Valentina', 'Gomez', 'valentina.gomez91@gmail.com', '3007788990', 1, 1);
INSERT INTO usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario)
VALUES ('1034567890', 'Andres', 'Lopez', 'andres.lopez77@gmail.com', '3126677889', 0, 2);
select u.idusuario,u.nombre,u.apellido from usuario as u order by u.nombre; 
select tu.nombre as tipoUsuario, count(*) as cantidad from usuario as u 
inner join tipousuario as tu on u.idTipoUsuario = tu.idtipoUsuario group by (tu.idtipoUsuario); 
select concat(nombre,' ',apellido) as nombreCompleto, case when estado = 1 then 'Activo' else 'Inactivo' end as estado from usuario;

