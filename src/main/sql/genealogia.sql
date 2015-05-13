CREATE DATABASE  IF NOT EXISTS `genealogia` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `genealogia`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: genealogia_dev
-- ------------------------------------------------------
-- Server version	5.6.14-log

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
-- Table structure for table `dokumenty`
--

DROP TABLE IF EXISTS `dokumenty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dokumenty` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tytul` varchar(255) DEFAULT NULL,
  `symbol` varchar(255) DEFAULT NULL,
  `opis` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dokumenty_osoby`
--

DROP TABLE IF EXISTS `dokumenty_osoby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dokumenty_osoby` (
  `id_osoby` int(11) unsigned NOT NULL,
  `id_dokumentu` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id_osoby`,`id_dokumentu`),
  KEY `fk_dokumenty_osoby_id_dokumentu_idx` (`id_dokumentu`),
  CONSTRAINT `fk_dokumenty_osoby_id_osoby` FOREIGN KEY (`id_osoby`) REFERENCES `osoby` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dokumenty_osoby_id_dokumentu` FOREIGN KEY (`id_dokumentu`) REFERENCES `dokumenty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `klany`
--

DROP TABLE IF EXISTS `klany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `klany` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(64) DEFAULT NULL,
  `opis` varchar(512) DEFAULT NULL,
  `korzen_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notatki`
--

DROP TABLE IF EXISTS `notatki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notatki` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_osoby` int(10) unsigned NOT NULL DEFAULT '0',
  `tresc` text,
  `tytul` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `osoby`
--

DROP TABLE IF EXISTS `osoby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `osoby` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `imiona` varchar(45) DEFAULT NULL,
  `nazwisko` varchar(128) DEFAULT NULL,
  `data_urodzenia` varchar(45) DEFAULT NULL,
  `miejsce_urodzenia` varchar(128) DEFAULT NULL,
  `data_smierci` varchar(45) DEFAULT NULL,
  `miejsce_smierci` varchar(128) DEFAULT NULL,
  `plec` varchar(10) DEFAULT NULL,
  `id_zwiazku_rodzicow` int(10) unsigned DEFAULT NULL,
  `data_pochowania` varchar(45) DEFAULT NULL,
  `miejsce_pochowania` varchar(128) DEFAULT NULL,
  `zawody_wykonywane` varchar(255) DEFAULT NULL,
  `opis` text,
  `wyksztalcenie` varchar(255) DEFAULT NULL,
  `miejsce_zamieszkania` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zwiazki`
--

DROP TABLE IF EXISTS `zwiazki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zwiazki` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_mezczyzny` int(10) unsigned NOT NULL DEFAULT '0',
  `id_kobiety` int(10) unsigned NOT NULL DEFAULT '0',
  `data_slubu` varchar(45) DEFAULT NULL,
  `miejsce_slubu` varchar(128) DEFAULT NULL,
  `data_poznania` varchar(45) DEFAULT NULL,
  `miejsce_poznania` varchar(128) DEFAULT NULL,
  `data_rozstania` varchar(45) DEFAULT NULL,
  `miejsce_rozstania` varchar(128) DEFAULT NULL,
  `data_rozwodu` varchar(45) DEFAULT NULL,
  `miejsce_rozwodu` varchar(128) DEFAULT NULL,
  `opis` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-12 22:08:50
