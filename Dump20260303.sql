-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: dbbiblioteca
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `autor`
--

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autor` (
  `idautor` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `nacionalidad` varchar(45) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  PRIMARY KEY (`idautor`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
INSERT INTO `autor` VALUES (1,'Gabriel','García Márquez','Colombiano','1927-03-06');
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `denegarCambioFechaNacimiento` BEFORE UPDATE ON `autor` FOR EACH ROW BEGIN
    if NEW.fechaNacimiento <> OLD.fechaNacimiento THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'No puedes modificar la fecha de nacimiento.';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `verificarDatosIgualesAutor` BEFORE UPDATE ON `autor` FOR EACH ROW BEGIN
    if NEW.nombre = OLD.nombre AND NEW.apellido = OLD.apellido AND NEW.nacionalidad = OLD.nacionalidad THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El valor ingresado es igual al ya existente ';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `idcategoria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` text NOT NULL,
  PRIMARY KEY (`idcategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'drama','Genero de polemica y entretenimiento'),(2,'accion','Genero de guearras , peleas y estrategias');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editorial`
--

DROP TABLE IF EXISTS `editorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editorial` (
  `ideditorial` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `pais` varchar(45) NOT NULL,
  `sitioWeb` varchar(45) NOT NULL,
  PRIMARY KEY (`ideditorial`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editorial`
--

LOCK TABLES `editorial` WRITE;
/*!40000 ALTER TABLE `editorial` DISABLE KEYS */;
INSERT INTO `editorial` VALUES (1,'la Rosa','Colombia','https://laRosa.com'),(2,'Editorial Planeta','Colombia','https://libros.com');
/*!40000 ALTER TABLE `editorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialmodificacion`
--

DROP TABLE IF EXISTS `historialmodificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historialmodificacion` (
  `idhistorialModificacion` int NOT NULL AUTO_INCREMENT,
  `registro` text NOT NULL,
  PRIMARY KEY (`idhistorialModificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialmodificacion`
--

LOCK TABLES `historialmodificacion` WRITE;
/*!40000 ALTER TABLE `historialmodificacion` DISABLE KEYS */;
INSERT INTO `historialmodificacion` VALUES (1,'Antiguo: idLibro: 1 titulo: Don quijote isbn: yggs45 añoPublicacion: 2026 numPag: 45 disponible: 1 ideditorial: 1Nuevo: idLibro: 1 titulo: El coronel no tiene quien le escriba isbn: yggs45 añoPublicacion: 2026 numPag: 45 disponible: 1 ideditorial: 1');
/*!40000 ALTER TABLE `historialmodificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libro` (
  `idlibro` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(45) NOT NULL,
  `isbn` varchar(45) NOT NULL,
  `añoPublicacion` year NOT NULL,
  `numPag` int NOT NULL,
  `disponible` tinyint NOT NULL,
  `idEditorial` int NOT NULL,
  PRIMARY KEY (`idlibro`),
  KEY `fk_libro_editorial1_idx` (`idEditorial`),
  CONSTRAINT `fk_libro_editorial1` FOREIGN KEY (`idEditorial`) REFERENCES `editorial` (`ideditorial`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES (1,'El coronel no tiene quien le escriba','yggs45',2026,45,1,1),(2,'Cien años de soledad','978-0307474728',1967,496,1,1);
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `guardarHistorialCambiosLibro` AFTER UPDATE ON `libro` FOR EACH ROW BEGIN
    if NEW.titulo <> OLD.titulo OR NEW.isbn <> OLD.isbn OR NEW.añoPublicacion <> OLD.añoPublicacion OR NEW.numPag <> OLD.numPag OR NEW.disponible <> OLD.disponible OR NEW.idEditorial <> OLD.idEditorial THEN
        INSERT INTO historialmodificacion (registro) values(concat('Antiguo: ','idLibro: ',OLD.idLibro,' titulo: ',OLD.titulo,' isbn: ',OLD.isbn,' añoPublicacion: ',OLD.añoPublicacion,' numPag: ',OLD.numPag,' disponible: ',OLD.disponible,' ideditorial: ',OLD.ideditorial
	,'Nuevo: ','idLibro: ',NEW.idLibro,' titulo: ',NEW.titulo,' isbn: ',NEW.isbn,' añoPublicacion: ',NEW.añoPublicacion,' numPag: ',NEW.numPag,' disponible: ',NEW.disponible,' ideditorial: ',NEW.ideditorial));
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `libroautor`
--

DROP TABLE IF EXISTS `libroautor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libroautor` (
  `idAutor` int NOT NULL,
  `idLibro` int NOT NULL,
  PRIMARY KEY (`idAutor`,`idLibro`),
  KEY `fk_libroAutor_libro_idx` (`idLibro`),
  CONSTRAINT `fk_libroAutor_autor1` FOREIGN KEY (`idAutor`) REFERENCES `autor` (`idautor`),
  CONSTRAINT `fk_libroAutor_libro` FOREIGN KEY (`idLibro`) REFERENCES `libro` (`idlibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libroautor`
--

LOCK TABLES `libroautor` WRITE;
/*!40000 ALTER TABLE `libroautor` DISABLE KEYS */;
/*!40000 ALTER TABLE `libroautor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librocategoria`
--

DROP TABLE IF EXISTS `librocategoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `librocategoria` (
  `idLibro` int NOT NULL,
  `idCategoria` int NOT NULL,
  PRIMARY KEY (`idLibro`,`idCategoria`),
  KEY `fk_libroCategoria_categoria1_idx` (`idCategoria`),
  CONSTRAINT `fk_libroCategoria_categoria1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idcategoria`),
  CONSTRAINT `fk_libroCategoria_libro1` FOREIGN KEY (`idLibro`) REFERENCES `libro` (`idlibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librocategoria`
--

LOCK TABLES `librocategoria` WRITE;
/*!40000 ALTER TABLE `librocategoria` DISABLE KEYS */;
INSERT INTO `librocategoria` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `librocategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `libroconcategorias`
--

DROP TABLE IF EXISTS `libroconcategorias`;
/*!50001 DROP VIEW IF EXISTS `libroconcategorias`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `libroconcategorias` AS SELECT 
 1 AS `nombreLibro`,
 1 AS `nombreCategoria`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `librospaginasmayorcien`
--

DROP TABLE IF EXISTS `librospaginasmayorcien`;
/*!50001 DROP VIEW IF EXISTS `librospaginasmayorcien`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `librospaginasmayorcien` AS SELECT 
 1 AS `idlibro`,
 1 AS `titulo`,
 1 AS `isbn`,
 1 AS `añoPublicacion`,
 1 AS `numPag`,
 1 AS `disponible`,
 1 AS `idEditorial`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `multa`
--

DROP TABLE IF EXISTS `multa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multa` (
  `idmulta` int NOT NULL AUTO_INCREMENT,
  `monto` double NOT NULL,
  `fechaGeneracion` date NOT NULL,
  `fechaPago` date NOT NULL,
  `estado` tinyint NOT NULL,
  `idPrestamo` int NOT NULL,
  PRIMARY KEY (`idmulta`),
  KEY `fk_multa_prestamo1_idx` (`idPrestamo`),
  CONSTRAINT `fk_multa_prestamo1` FOREIGN KEY (`idPrestamo`) REFERENCES `prestamo` (`idprestamo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multa`
--

LOCK TABLES `multa` WRITE;
/*!40000 ALTER TABLE `multa` DISABLE KEYS */;
/*!40000 ALTER TABLE `multa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `idprestamo` int NOT NULL AUTO_INCREMENT,
  `fechaPrestamo` date NOT NULL,
  `fechaDevolucionEsperada` date NOT NULL,
  `fechaDevolucionReal` date NOT NULL,
  `estado` tinyint NOT NULL,
  `idLibro` int NOT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idprestamo`),
  KEY `fk_prestamo_libro1_idx` (`idLibro`),
  CONSTRAINT `fk_prestamo_libro1` FOREIGN KEY (`idLibro`) REFERENCES `libro` (`idlibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipousuario` (
  `idtipoUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idtipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'Cliente'),(2,'Administrador');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` int NOT NULL AUTO_INCREMENT,
  `documento` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `estado` tinyint NOT NULL,
  `idTipoUsuario` int NOT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `fk_usuario_tipoUsuario1_idx` (`idTipoUsuario`),
  CONSTRAINT `fk_usuario_tipoUsuario1` FOREIGN KEY (`idTipoUsuario`) REFERENCES `tipousuario` (`idtipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (7,'10554879564','Jose','Alvarez','JoseAlvares34@gmail.com','3134556879',1,1),(8,'1023456789','Laura','Martinez','laura.martinez89@gmail.com','3205567788',1,1),(9,'1098765432','Camilo','Rodriguez','camilo.rodriguez23@gmail.com','3114456677',1,2),(10,'1002345678','Valentina','Gomez','valentina.gomez91@gmail.com','3007788990',1,1),(11,'1034567890','Andres','Lopez','andres.lopez77@gmail.com','3126677889',0,2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `usuariocontipousuario`
--

DROP TABLE IF EXISTS `usuariocontipousuario`;
/*!50001 DROP VIEW IF EXISTS `usuariocontipousuario`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `usuariocontipousuario` AS SELECT 
 1 AS `idusuario`,
 1 AS `documento`,
 1 AS `nombre`,
 1 AS `apellido`,
 1 AS `email`,
 1 AS `telefono`,
 1 AS `estado`,
 1 AS `idTipoUsuario`,
 1 AS `tipoUsuario`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'dbbiblioteca'
--

--
-- Dumping routines for database 'dbbiblioteca'
--
/*!50003 DROP PROCEDURE IF EXISTS `añadirCategoriaLibro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `añadirCategoriaLibro`(in pIdLibro int, in pIdCategoria int)
BEGIN
insert into librocategoria (idLibro,idCategoria) values (pIdLibro,pIdCategoria);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `buscarLibroPorNombre` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarLibroPorNombre`(in pNombre varchar(50))
BEGIN
select * from libro where nombre = pNombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiarEstadoUsuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cambiarEstadoUsuario`(in pIdUsuario int,in PEstado boolean)
BEGIN
update usuario set estado = pEstado where (idUsuario = pIdUsuario);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `crearCategoria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `crearCategoria`(in pNombre varchar(50), in pDescripcion text)
BEGIN
insert into categoria (nombre,descripcion) values (pNombre,pDescripcion);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `crearLibro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `crearLibro`(in pTitulo varchar(50), in pIsbn varchar(50), in pAñoPublicacion year , in pNumPag int, in pDisponible boolean, in pIdEditorial int)
BEGIN
insert into libro (titulo,isbn,añoPublicacion,numPag,disponible,idEditorial) values (pTitulo,pIsbn,pAñoPublicacion,pNumPag,pDisponible,pIdEditorial);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `listarLibrosConCategoria` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8mb3_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `listarLibrosConCategoria`(in pIdLibro int)
BEGIN
select l.titulo as libro, c.nombre as categoria from libro as l inner join librocategoria as lc on l.idlibro = lc.idLibro inner join categoria as c on lc.idcategoria = c.idcategoria where l.idLibro = pIdLibro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `libroconcategorias`
--

/*!50001 DROP VIEW IF EXISTS `libroconcategorias`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `libroconcategorias` AS select `l`.`titulo` AS `nombreLibro`,`c`.`nombre` AS `nombreCategoria` from ((`libro` `l` join `librocategoria` `lc` on((`l`.`idlibro` = `lc`.`idLibro`))) join `categoria` `c` on((`lc`.`idCategoria` = `c`.`idcategoria`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `librospaginasmayorcien`
--

/*!50001 DROP VIEW IF EXISTS `librospaginasmayorcien`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `librospaginasmayorcien` AS select `libro`.`idlibro` AS `idlibro`,`libro`.`titulo` AS `titulo`,`libro`.`isbn` AS `isbn`,`libro`.`añoPublicacion` AS `añoPublicacion`,`libro`.`numPag` AS `numPag`,`libro`.`disponible` AS `disponible`,`libro`.`idEditorial` AS `idEditorial` from `libro` where (`libro`.`numPag` > 100) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `usuariocontipousuario`
--

/*!50001 DROP VIEW IF EXISTS `usuariocontipousuario`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `usuariocontipousuario` AS select `u`.`idusuario` AS `idusuario`,`u`.`documento` AS `documento`,`u`.`nombre` AS `nombre`,`u`.`apellido` AS `apellido`,`u`.`email` AS `email`,`u`.`telefono` AS `telefono`,`u`.`estado` AS `estado`,`u`.`idTipoUsuario` AS `idTipoUsuario`,`tu`.`nombre` AS `tipoUsuario` from (`usuario` `u` join `tipousuario` `tu` on((`u`.`idTipoUsuario` = `tu`.`idtipoUsuario`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-03 18:03:53
