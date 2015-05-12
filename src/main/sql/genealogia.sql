CREATE DATABASE  IF NOT EXISTS `genealogia` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `genealogia`;

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8 COMMENT='Lista osób rodziny';
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
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;