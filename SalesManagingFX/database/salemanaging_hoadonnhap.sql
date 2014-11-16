CREATE DATABASE  IF NOT EXISTS `salemanaging` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `salemanaging`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: salemanaging
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hoadonnhap`
--

DROP TABLE IF EXISTS `hoadonnhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hoadonnhap` (
  `mahoadonnhap` int(11) NOT NULL AUTO_INCREMENT,
  `ngaytra` date DEFAULT NULL,
  `tongsotien` int(11) DEFAULT NULL,
  `datra` int(11) DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL,
  `mahanghoa` int(11) NOT NULL,
  `idnhacungcap` int(11) NOT NULL,
  PRIMARY KEY (`mahoadonnhap`),
  KEY `mahanghoa` (`mahanghoa`),
  KEY `idnhacungcap` (`idnhacungcap`),
  CONSTRAINT `hoadonnhap_ibfk_1` FOREIGN KEY (`mahanghoa`) REFERENCES `kho` (`mahanghoa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `hoadonnhap_ibfk_2` FOREIGN KEY (`idnhacungcap`) REFERENCES `nhacungcap` (`idnhacungcap`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadonnhap`
--

LOCK TABLES `hoadonnhap` WRITE;
/*!40000 ALTER TABLE `hoadonnhap` DISABLE KEYS */;
INSERT INTO `hoadonnhap` VALUES (1,'2014-10-31',16000,16000,1,1,1),(2,'2014-10-31',32000,0,2,1,1),(3,'2014-10-31',16000,0,1,1,1),(4,'2014-10-31',192000,190000,12,1,1),(5,'2014-10-31',192000,190000,12,1,1),(6,'2014-11-03',192000,192000,12,1,1),(7,'2014-11-03',32000,32000,2,1,1),(8,'2014-11-03',16000,0,1,1,1),(9,'2014-11-15',216000,216000,12,2,2);
/*!40000 ALTER TABLE `hoadonnhap` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-16  9:44:55
