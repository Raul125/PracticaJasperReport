-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.6.2-MariaDB-ubu2404 - mariadb.org binary distribution
-- SO del servidor:              debian-linux-gnu
-- HeidiSQL Versión:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para FlotaEspacial
CREATE DATABASE IF NOT EXISTS `FlotaEspacial` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `FlotaEspacial`;

-- Volcando estructura para tabla FlotaEspacial.misiones
CREATE TABLE IF NOT EXISTS `misiones` (
  `id_mision` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` text NOT NULL,
  `id_nave` int(11) DEFAULT NULL,
  `estado` enum('Completada','Fallida','En progreso') NOT NULL,
  PRIMARY KEY (`id_mision`),
  KEY `id_nave` (`id_nave`),
  CONSTRAINT `misiones_ibfk_1` FOREIGN KEY (`id_nave`) REFERENCES `naves_espaciales` (`id_nave`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla FlotaEspacial.naves_espaciales
CREATE TABLE IF NOT EXISTS `naves_espaciales` (
  `id_nave` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_nave` varchar(100) NOT NULL,
  `clase` enum('Exploración','Combate','Transporte') NOT NULL,
  `capacidad_tripulacion` int(11) NOT NULL CHECK (`capacidad_tripulacion` > 0),
  PRIMARY KEY (`id_nave`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla FlotaEspacial.tripulantes
CREATE TABLE IF NOT EXISTS `tripulantes` (
  `id_tripulante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_tripulante` varchar(100) NOT NULL,
  `rango` enum('Capitán','Ingeniero','Piloto') NOT NULL,
  `id_nave` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tripulante`),
  KEY `id_nave` (`id_nave`),
  CONSTRAINT `tripulantes_ibfk_1` FOREIGN KEY (`id_nave`) REFERENCES `naves_espaciales` (`id_nave`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
