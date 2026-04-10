-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema productores_agropecuarios_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema productores_agropecuarios_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `productores_agropecuarios_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `productores_agropecuarios_db` ;

-- -----------------------------------------------------
-- Table `productores_agropecuarios_db`.`productores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `productores_agropecuarios_db`.`productores` (
  `id_productores` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(80) NOT NULL,
  `apellidos` VARCHAR(80) NOT NULL,
  `cedula` VARCHAR(20) NOT NULL,
  `vereda` VARCHAR(80) NOT NULL,
  `telefono` VARCHAR(20) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `producto_principal` VARCHAR(40) NOT NULL,
  `hectarea` DECIMAL(6,2) NOT NULL,
  `tiene_riego` TINYINT NOT NULL,
  `observacion` VARCHAR(300) NULL DEFAULT NULL,
  `fecha_registro` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_productores`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `productores_agropecuarios_db`.`tipousuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `productores_agropecuarios_db`.`tipousuario` (
  `idtipoUsuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipoUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `productores_agropecuarios_db`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `productores_agropecuarios_db`.`usuario` (
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
    REFERENCES `productores_agropecuarios_db`.`tipousuario` (`idtipoUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
