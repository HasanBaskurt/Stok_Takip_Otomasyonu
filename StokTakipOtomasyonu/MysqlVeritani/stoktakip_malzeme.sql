-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: stoktakip
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `malzeme`
--

DROP TABLE IF EXISTS `malzeme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `malzeme` (
  `id_malzeme` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(50) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `alisFiyat` int NOT NULL,
  `satisFiyat` int NOT NULL,
  `adet` int NOT NULL,
  `toplamKarZarar` int NOT NULL,
  PRIMARY KEY (`id_malzeme`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `malzeme`
--

LOCK TABLES `malzeme` WRITE;
/*!40000 ALTER TABLE `malzeme` DISABLE KEYS */;
INSERT INTO `malzeme` VALUES (1,'Telefon','Elek',3000,3100,5,500),(2,'Ütü','Elektronik',300,600,3,900),(6,'Saat','Ev Eşyası',100,3000,3,8700),(8,'Koltuk','Ev Eşyası',2500,3000,20,10000),(12,'Masa','Ev Eşyası',1000,2000,5,5000),(13,'Televizyon','Elektronik',5000,5500,10,5000),(14,'Tablet','Elektronik',1500,2000,20,10000),(16,'Dolap','Ev Eşyası',2000,2500,7,3500),(18,'Gece Lambası','Ev Eşyası',200,500,10,3000),(23,'Halı','Ev Eşyası',500,700,5,1000);
/*!40000 ALTER TABLE `malzeme` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-20 16:01:44
