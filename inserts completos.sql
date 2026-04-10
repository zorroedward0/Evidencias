use productores_agropecuarios_db;
-- Tiposusuario
INSERT INTO tipousuario (idtipoUsuario, nombre) VALUES 
(1, 'Administrador'),
(2, 'Cliente');

-- Usuarios
INSERT INTO usuario (documento, nombre, apellido, email, telefono, estado, idTipoUsuario, password) VALUES 
('1049654321', 'Ricardo', 'Almanza', 'r.almanza@agrored.com', '3104556677', 1, 1, '123'),
('1052334455', 'Sandra', 'Milena', 'smilena@agrored.com', '3208889900', 1, 1, '123'),
('1090223344', 'Carlos', 'Mario', 'cmario92@gmail.com', '3152223344', 1, 2, '123'),
('1015667788', 'Beatriz', 'Pinzón', 'betty.pinzon@outlook.com', '3114445566', 1, 2, '123'),
('1033445566', 'Julián', 'Andrés', 'jandres_88@hotmail.com', '3126667788', 1, 2, '123'),
('1088554433', 'Mónica', 'Suarez', 'msuarez_campo@yahoo.com', '3183334455', 1, 2, '123'),
('1077665544', 'Wilson', 'Gallego', 'wilson.gallego@gmail.com', '3005556677', 0, 2, '123'),
('1044332211', 'Claudia', 'Vargas', 'claudia.vargas.agri@gmail.com', '3149990011', 1, 2, '123'),
('1022339900', 'Fernando', 'Gaitán', 'fgaitan.productor@outlook.com', '3177778899', 1, 2, '123'),
('1066778899', 'Patricia', 'Fernández', 'patricia.fdez@gmail.com', '3161112233', 1, 2, '123');

-- Productores
INSERT INTO productores (nombres, apellidos, cedula, vereda, telefono, correo, producto_principal, hectarea, tiene_riego, observacion, fecha_registro) VALUES 
('Luis Alberto', 'Rodríguez', '74332110', 'El Pantano', '3104445511', 'lrodriguez@miagro.com', 'Café Pergamino', 4.50, 1, 'Finca La Esperanza, requiere renovación de cafetales.', '2024-03-20 08:30:00'),
('Martha Cecilia', 'Gómez', '40332551', 'La Chapa', '3205556622', 'martha.gomez@gmail.com', 'Cacao', 10.25, 0, 'Terreno con buena sombra natural.', '2024-03-21 09:45:00'),
('José Ignacio', 'López', '74112233', 'Sirivana', '3156667733', 'ignacio.lopez@yahoo.com', 'Arroz', 25.00, 1, 'Cuenta con sistema de riego por canales.', '2024-03-21 14:15:00'),
('Carmen Rosa', 'Suarez', '23445566', 'El Venado', '3117778844', 'carmensuarez@outlook.com', 'Palma de Aceite', 30.00, 1, 'Producción constante, asociado a cooperativa.', '2024-03-22 10:00:00'),
('Hernando', 'Pérez', '79554433', 'Tamarindo', '3128889955', 'hernandop@gmail.com', 'Maíz Amarillo', 12.80, 0, 'Suelo arcilloso, requiere drenajes.', '2024-03-22 11:30:00'),
('Gloria Stella', 'Morales', '32114455', 'La Unión', '3189990066', 'stella.morales@miagro.com', 'Aguacate Hass', 3.20, 1, 'Certificación Global GAP en proceso.', '2024-03-23 15:20:00'),
('Gabriel', 'Hernández', '19443322', 'San Rafael', '3141112277', 'gabriel_h@gmail.com', 'Plátano Hartón', 6.00, 0, 'Cultivo intercalado con café.', '2024-03-24 07:10:00'),
('Yolanda', 'Díaz', '51667788', 'Los Olivos', '3172223388', 'yolandad@gmail.com', 'Flores de Corte', 2.10, 1, 'Cultivo bajo invernadero tecnificado.', '2024-03-24 16:40:00'),
('Alvaro', 'Uribe', '70221100', 'El Recreo', '3163334499', 'alvaro.recreo@outlook.com', 'Ganadería de Leche', 50.00, 1, 'Pastos mejorados y ordeño mecánico.', '2024-03-25 08:00:00'),
('Esperanza', 'Castillo', '20887766', 'Bellavista', '3004445500', 'espe_castillo@gmail.com', 'Cebolla Junca', 1.50, 1, 'Pequeña productora, venta en mercado local.', '2024-03-25 12:15:00');
