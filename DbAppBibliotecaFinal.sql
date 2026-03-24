-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dbbiblioteca
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbbiblioteca
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbbiblioteca` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `dbbiblioteca` ;

-- -----------------------------------------------------
-- Table `dbbiblioteca`.`autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`autor` (
  `idautor` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `nacionalidad` VARCHAR(45) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  PRIMARY KEY (`idautor`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`categoria` (
  `idcategoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` TEXT NOT NULL,
  PRIMARY KEY (`idcategoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`editorial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`editorial` (
  `ideditorial` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  `sitioWeb` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ideditorial`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`libro` (
  `idlibro` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `isbn` VARCHAR(45) NOT NULL,
  `añoPublicacion` YEAR NOT NULL,
  `numPag` INT NOT NULL,
  `disponible` TINYINT NOT NULL,
  `idEditorial` INT NOT NULL,
  PRIMARY KEY (`idlibro`),
  INDEX `fk_libro_editorial1_idx` (`idEditorial` ASC) VISIBLE,
  CONSTRAINT `fk_libro_editorial1`
    FOREIGN KEY (`idEditorial`)
    REFERENCES `dbbiblioteca`.`editorial` (`ideditorial`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`libroautor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`libroautor` (
  `idAutor` INT NOT NULL,
  `idLibro` INT NOT NULL,
  PRIMARY KEY (`idAutor`, `idLibro`),
  INDEX `fk_libroAutor_libro_idx` (`idLibro` ASC) VISIBLE,
  CONSTRAINT `fk_libroAutor_autor1`
    FOREIGN KEY (`idAutor`)
    REFERENCES `dbbiblioteca`.`autor` (`idautor`),
  CONSTRAINT `fk_libroAutor_libro`
    FOREIGN KEY (`idLibro`)
    REFERENCES `dbbiblioteca`.`libro` (`idlibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`librocategoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`librocategoria` (
  `idLibro` INT NOT NULL,
  `idCategoria` INT NOT NULL,
  PRIMARY KEY (`idLibro`, `idCategoria`),
  INDEX `fk_libroCategoria_categoria1_idx` (`idCategoria` ASC) VISIBLE,
  CONSTRAINT `fk_libroCategoria_categoria1`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `dbbiblioteca`.`categoria` (`idcategoria`),
  CONSTRAINT `fk_libroCategoria_libro1`
    FOREIGN KEY (`idLibro`)
    REFERENCES `dbbiblioteca`.`libro` (`idlibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`prestamo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`prestamo` (
  `idprestamo` INT NOT NULL AUTO_INCREMENT,
  `fechaPrestamo` DATE NOT NULL,
  `fechaDevolucionEsperada` DATE NOT NULL,
  `fechaDevolucionReal` DATE NULL DEFAULT NULL,
  `estado` TINYINT NOT NULL,
  `idLibro` INT NOT NULL,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`idprestamo`),
  INDEX `fk_prestamo_libro1_idx` (`idLibro` ASC) VISIBLE,
  CONSTRAINT `fk_prestamo_libro1`
    FOREIGN KEY (`idLibro`)
    REFERENCES `dbbiblioteca`.`libro` (`idlibro`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`tipousuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`tipousuario` (
  `idtipoUsuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipoUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `dbbiblioteca`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `documento` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NOT NULL,
  `estado` TINYINT NOT NULL,
  `idTipoUsuario` INT NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idusuario`),
  INDEX `fk_usuario_tipoUsuario1_idx` (`idTipoUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_tipoUsuario1`
    FOREIGN KEY (`idTipoUsuario`)
    REFERENCES `dbbiblioteca`.`tipousuario` (`idtipoUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `dbbiblioteca` ;

-- -----------------------------------------------------
-- Placeholder table for view `dbbiblioteca`.`libroconcategorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`libroconcategorias` (`nombreLibro` INT, `nombreCategoria` INT);

-- -----------------------------------------------------
-- Placeholder table for view `dbbiblioteca`.`librospaginasmayorcien`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`librospaginasmayorcien` (`idlibro` INT, `titulo` INT, `isbn` INT, `añoPublicacion` INT, `numPag` INT, `disponible` INT, `idEditorial` INT);

-- -----------------------------------------------------
-- Placeholder table for view `dbbiblioteca`.`usuariocontipousuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbbiblioteca`.`usuariocontipousuario` (`idusuario` INT, `documento` INT, `nombre` INT, `apellido` INT, `email` INT, `telefono` INT, `estado` INT, `idTipoUsuario` INT, `tipoUsuario` INT);

-- -----------------------------------------------------
-- procedure añadirCategoriaLibro
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `añadirCategoriaLibro`(in pIdLibro int, in pIdCategoria int)
BEGIN
insert into librocategoria (idLibro,idCategoria) values (pIdLibro,pIdCategoria);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure buscarLibroPorNombre
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarLibroPorNombre`(in pNombre varchar(50))
BEGIN
select * from libro where nombre = pNombre;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure cambiarEstadoUsuario
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cambiarEstadoUsuario`(in pIdUsuario int,in PEstado boolean)
BEGIN
update usuario set estado = pEstado where (idUsuario = pIdUsuario);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure crearCategoria
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `crearCategoria`(in pNombre varchar(50), in pDescripcion text)
BEGIN
insert into categoria (nombre,descripcion) values (pNombre,pDescripcion);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure crearLibro
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `crearLibro`(in pTitulo varchar(50), in pIsbn varchar(50), in pAñoPublicacion year , in pNumPag int, in pDisponible boolean, in pIdEditorial int)
BEGIN
insert into libro (titulo,isbn,añoPublicacion,numPag,disponible,idEditorial) values (pTitulo,pIsbn,pAñoPublicacion,pNumPag,pDisponible,pIdEditorial);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure listarLibrosConCategoria
-- -----------------------------------------------------

DELIMITER $$
USE `dbbiblioteca`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `listarLibrosConCategoria`(in pIdLibro int)
BEGIN
select l.titulo as libro, c.nombre as categoria from libro as l inner join librocategoria as lc on l.idlibro = lc.idLibro inner join categoria as c on lc.idcategoria = c.idcategoria where l.idLibro = pIdLibro;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `dbbiblioteca`.`libroconcategorias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbbiblioteca`.`libroconcategorias`;
USE `dbbiblioteca`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbbiblioteca`.`libroconcategorias` AS select `l`.`titulo` AS `nombreLibro`,`c`.`nombre` AS `nombreCategoria` from ((`dbbiblioteca`.`libro` `l` join `dbbiblioteca`.`librocategoria` `lc` on((`l`.`idlibro` = `lc`.`idLibro`))) join `dbbiblioteca`.`categoria` `c` on((`lc`.`idCategoria` = `c`.`idcategoria`)));

-- -----------------------------------------------------
-- View `dbbiblioteca`.`librospaginasmayorcien`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbbiblioteca`.`librospaginasmayorcien`;
USE `dbbiblioteca`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbbiblioteca`.`librospaginasmayorcien` AS select `dbbiblioteca`.`libro`.`idlibro` AS `idlibro`,`dbbiblioteca`.`libro`.`titulo` AS `titulo`,`dbbiblioteca`.`libro`.`isbn` AS `isbn`,`dbbiblioteca`.`libro`.`añoPublicacion` AS `añoPublicacion`,`dbbiblioteca`.`libro`.`numPag` AS `numPag`,`dbbiblioteca`.`libro`.`disponible` AS `disponible`,`dbbiblioteca`.`libro`.`idEditorial` AS `idEditorial` from `dbbiblioteca`.`libro` where (`dbbiblioteca`.`libro`.`numPag` > 100);

-- -----------------------------------------------------
-- View `dbbiblioteca`.`usuariocontipousuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dbbiblioteca`.`usuariocontipousuario`;
USE `dbbiblioteca`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dbbiblioteca`.`usuariocontipousuario` AS select `u`.`idusuario` AS `idusuario`,`u`.`documento` AS `documento`,`u`.`nombre` AS `nombre`,`u`.`apellido` AS `apellido`,`u`.`email` AS `email`,`u`.`telefono` AS `telefono`,`u`.`estado` AS `estado`,`u`.`idTipoUsuario` AS `idTipoUsuario`,`tu`.`nombre` AS `tipoUsuario` from (`dbbiblioteca`.`usuario` `u` join `dbbiblioteca`.`tipousuario` `tu` on((`u`.`idTipoUsuario` = `tu`.`idtipoUsuario`)));
USE `dbbiblioteca`;

DELIMITER $$
USE `dbbiblioteca`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `dbbiblioteca`.`denegarCambioFechaNacimiento`
BEFORE UPDATE ON `dbbiblioteca`.`autor`
FOR EACH ROW
BEGIN
    if NEW.fechaNacimiento <> OLD.fechaNacimiento THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'No puedes modificar la fecha de nacimiento.';
    END IF;
END$$

USE `dbbiblioteca`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `dbbiblioteca`.`verificarDatosIgualesAutor`
BEFORE UPDATE ON `dbbiblioteca`.`autor`
FOR EACH ROW
BEGIN
    if NEW.nombre = OLD.nombre AND NEW.apellido = OLD.apellido AND NEW.nacionalidad = OLD.nacionalidad THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El valor ingresado es igual al ya existente ';
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
