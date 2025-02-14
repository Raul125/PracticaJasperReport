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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla FlotaEspacial.misiones: ~20 rows (aproximadamente)
INSERT INTO `misiones` (`id_mision`, `descripcion`, `id_nave`, `estado`) VALUES
	(1, 'Exploración del sistema Alpha Centauri', 1, 'Completada'),
	(2, 'Batalla en la Nebulosa Roja', 2, 'Fallida'),
	(3, 'Transporte de suministros a la estación lunar', 3, 'En progreso'),
	(4, 'Reconocimiento en el cinturón de asteroides', 1, 'Completada'),
	(5, 'Combate en el sector Omega', 2, 'Completada'),
	(6, 'Entrega urgente a la base Delta', 3, 'Fallida'),
	(7, 'Operación rescate en el planeta X', 4, 'En progreso'),
	(8, 'Patrulla espacial en la frontera', 5, 'Completada'),
	(9, 'Defensa del sector Bravo', 6, 'Fallida'),
	(10, 'Misión diplomática en el sistema Zeta', 7, 'Completada'),
	(11, 'Exploración de un nuevo planeta', 1, 'En progreso'),
	(12, 'Batalla intergaláctica en el sector 9', 2, 'Fallida'),
	(13, 'Transporte de colonos a la estación espacial', 3, 'Completada'),
	(14, 'Operación sigilosa en territorio enemigo', 4, 'Completada'),
	(15, 'Misión de reconocimiento en el borde de la galaxia', 5, 'En progreso'),
	(16, 'Ataque sorpresa en la luna oculta', 6, 'Fallida'),
	(17, 'Rescate en la zona de guerra', 7, 'Completada'),
	(18, 'Operación de rutina en el espacio profundo', 1, 'Completada'),
	(19, 'Patrulla nocturna en el sector Delta', 4, 'En progreso'),
	(20, 'Entrega de repuestos a la nave nodriza', 3, 'Completada');

-- Volcando estructura para tabla FlotaEspacial.naves_espaciales
CREATE TABLE IF NOT EXISTS `naves_espaciales` (
  `id_nave` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_nave` varchar(100) NOT NULL,
  `clase` enum('Exploración','Combate','Transporte') NOT NULL,
  `capacidad_tripulacion` int(11) NOT NULL CHECK (`capacidad_tripulacion` > 0),
  PRIMARY KEY (`id_nave`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla FlotaEspacial.naves_espaciales: ~7 rows (aproximadamente)
INSERT INTO `naves_espaciales` (`id_nave`, `nombre_nave`, `clase`, `capacidad_tripulacion`) VALUES
	(1, 'USS Explorer', 'Exploración', 50),
	(2, 'Destroyer X', 'Combate', 100),
	(3, 'Galaxy Transport', 'Transporte', 200),
	(4, 'Voyager', 'Exploración', 75),
	(5, 'Falcon Fighter', 'Combate', 80),
	(6, 'Stellar Carrier', 'Transporte', 150),
	(7, 'Nebula Runner', 'Exploración', 60);

-- Volcando estructura para tabla FlotaEspacial.tripulantes
CREATE TABLE IF NOT EXISTS `tripulantes` (
  `id_tripulante` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_tripulante` varchar(100) NOT NULL,
  `rango` enum('Capitán','Ingeniero','Piloto') NOT NULL,
  `id_nave` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tripulante`),
  KEY `id_nave` (`id_nave`),
  CONSTRAINT `tripulantes_ibfk_1` FOREIGN KEY (`id_nave`) REFERENCES `naves_espaciales` (`id_nave`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla FlotaEspacial.tripulantes: ~20 rows (aproximadamente)
INSERT INTO `tripulantes` (`id_tripulante`, `nombre_tripulante`, `rango`, `id_nave`) VALUES
	(1, 'John Doe', 'Capitán', 1),
	(2, 'Jane Smith', 'Ingeniero', 1),
	(3, 'Mike Johnson', 'Piloto', 2),
	(4, 'Sara Connor', 'Capitán', 3),
	(5, 'Alice Johnson', 'Ingeniero', 2),
	(6, 'Bob Martin', 'Piloto', 3),
	(7, 'Clark Kent', 'Capitán', 4),
	(8, 'Bruce Wayne', 'Ingeniero', 4),
	(9, 'Diana Prince', 'Piloto', 5),
	(10, 'Barry Allen', 'Ingeniero', 5),
	(11, 'Hal Jordan', 'Capitán', 6),
	(12, 'Arthur Curry', 'Piloto', 7),
	(13, 'Victor Stone', 'Ingeniero', 7),
	(14, 'Shazam', 'Capitán', 2),
	(15, 'Wally West', 'Piloto', 4),
	(16, 'Kara Danvers', 'Capitán', 3),
	(17, 'Lois Lane', 'Ingeniero', 1),
	(18, 'Oliver Queen', 'Piloto', 6),
	(19, 'Barry Allen Jr.', 'Ingeniero', 5),
	(20, 'Felicity Smoak', 'Piloto', 4);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
