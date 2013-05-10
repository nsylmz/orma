CREATE DATABASE  IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stock`;
-- MySQL dump 10.13  Distrib 5.6.10, for Win32 (x86)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Table structure for table `warehouse_record`
--

DROP TABLE IF EXISTS `warehouse_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRANSACTION_TIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `AMOUNT` bigint(20) DEFAULT NULL,
  `BILL_NUMBER` bigint(20) DEFAULT NULL,
  `DEPLOYMENT_DATE` date DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(255) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL,
  `WAREHOUSE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK97BA656D2B8C97BA` (`WAREHOUSE_ID`),
  KEY `FK97BA656D4F3A103A` (`PRODUCT_ID`),
  CONSTRAINT `FK97BA656D2B8C97BA` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `warehouse` (`id`),
  CONSTRAINT `FK97BA656D4F3A103A` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_record`
--

LOCK TABLES `warehouse_record` WRITE;
/*!40000 ALTER TABLE `warehouse_record` DISABLE KEYS */;
INSERT INTO `warehouse_record` VALUES (1,'2013-04-20 15:38:14',0,280,0,'2013-03-01','girdi',1,4),(2,'2013-04-20 15:38:15',0,10,0,'2013-03-03','çıktı',1,4),(3,'2013-04-20 15:38:15',0,10,0,'2013-04-01','çıktı',1,4),(4,'2013-04-20 15:38:15',0,10,0,'2013-04-09','çıktı',1,4),(5,'2013-04-20 16:06:14',0,273,0,'2013-03-01','girdi',2,4),(6,'2013-04-20 16:06:14',0,9,0,'2013-03-04','çıktı',2,4),(7,'2013-04-20 16:06:15',0,9,0,'2013-04-01','çıktı',2,4),(8,'2013-04-20 16:06:15',0,18,0,'2013-04-02','çıktı',2,4),(9,'2013-04-20 16:06:15',0,9,0,'2013-04-09','çıktı',2,4),(10,'2013-04-20 16:06:15',0,9,0,'2013-04-17','çıktı',2,4),(11,'2013-04-20 16:08:29',0,378,0,'2013-03-01','girdi',3,4),(12,'2013-04-20 16:08:30',0,10,0,'2013-03-04','çıktı',3,4),(13,'2013-04-20 16:08:30',0,10,0,'2013-04-01','çıktı',3,4),(14,'2013-04-20 16:08:30',0,30,0,'2013-04-02','çıktı',3,4),(15,'2013-04-20 16:08:30',0,10,0,'2013-04-09','çıktı',3,4),(16,'2013-04-20 16:08:30',0,10,0,'2013-04-17','çıktı',3,4),(17,'2013-04-24 08:55:01',1,2592,0,'2013-03-01','girdi',4,4),(18,'2013-04-24 08:55:01',1,96,0,'2013-03-05','çıktı',4,4),(19,'2013-04-24 08:55:01',1,96,0,'2013-03-11','çıktı',4,4),(20,'2013-04-24 08:55:01',1,96,0,'2013-03-22','çıktı',4,4),(21,'2013-04-24 08:55:01',1,64,0,'2013-04-09','çıktı',4,4),(22,'2013-04-24 08:55:01',1,64,0,'2013-04-17','çıktı',4,4),(23,'2013-04-24 09:15:49',1,486,0,'2013-03-01','girdi',5,4),(24,'2013-04-24 09:15:49',1,66,0,'2013-04-01','çıktı',5,4),(25,'2013-04-24 09:15:49',1,60,0,'2013-04-04','çıktı',5,4),(26,'2013-04-24 09:15:49',1,390,0,'2013-04-10','girdi',5,4),(27,'2013-04-24 09:15:50',1,78,0,'2013-04-10','çıktı',5,4),(28,'2013-04-20 16:17:46',0,72,0,'2013-03-01','girdi',6,4),(29,'2013-04-24 08:50:04',1,1872,0,'2013-03-01','girdi',7,4),(30,'2013-04-24 09:19:29',1,2000,0,'2013-04-10','girdi',10,4),(31,'2013-04-24 09:21:08',1,252,0,'2013-03-01','girdi',11,4),(32,'2013-04-24 09:21:08',1,27,0,'2013-03-05','çıktı',11,4),(33,'2013-04-24 09:21:08',1,45,0,'2013-03-11','çıktı',11,4),(34,'2013-04-24 09:21:08',1,45,0,'2013-04-01','çıktı',11,4),(35,'2013-04-24 09:22:15',1,400,0,'2013-03-01','girdi',12,4),(36,'2013-04-24 09:29:09',1,2544,0,'2013-03-01','girdi',9,4),(37,'2013-04-24 09:29:09',1,48,0,'2013-03-04','çıktı',9,4),(38,'2013-04-24 09:29:09',1,192,0,'2013-03-07','çıktı',9,4),(39,'2013-04-24 09:29:09',1,96,0,'2013-04-04','çıktı',9,4),(40,'2013-04-24 09:29:09',1,96,0,'2013-04-09','çıktı',9,4),(41,'2013-04-24 09:32:15',1,1440,0,'2013-03-01','girdi',8,4),(42,'2013-04-24 09:32:15',1,72,0,'2013-03-05','çıktı',8,4),(43,'2013-04-24 09:32:15',1,72,0,'2013-04-01','çıktı',8,4),(44,'2013-04-24 09:32:15',1,72,0,'2013-04-09','çıktı',8,4),(45,'2013-04-24 09:13:14',1,2160,0,'2013-03-01','girdi',13,4),(46,'2013-04-24 09:13:14',1,48,0,'2013-03-05','çıktı',13,4),(47,'2013-04-24 09:13:14',1,48,0,'2013-03-11','çıktı',13,4),(48,'2013-04-24 09:13:14',1,64,0,'2013-03-22','çıktı',13,4),(49,'2013-04-24 09:13:14',1,48,0,'2013-04-04','çıktı',13,4),(50,'2013-04-24 09:13:14',1,32,0,'2013-04-09','çıktı',13,4),(51,'2013-04-24 09:13:36',2,32,0,'2013-04-17','çıktı',13,4),(52,'2013-04-20 16:50:08',0,84,0,'2013-03-02','girdi',14,4),(53,'2013-04-20 16:50:08',0,10,0,'2013-03-04','çıktı',14,4),(54,'2013-04-20 16:50:08',0,10,0,'2013-04-01','çıktı',14,4),(55,'2013-04-20 16:50:08',0,8,0,'2013-04-02','çıktı',14,4),(57,'2013-04-24 04:50:53',0,69,0,'2013-03-01','girdi',15,3),(58,'2013-04-24 09:08:49',0,32,0,'2013-04-22','çıktı',4,4),(59,'2013-04-24 09:13:52',1,16,0,'2013-04-22','çıktı',13,4),(60,'2013-04-24 09:25:40',0,288,0,'2013-03-01','girdi',16,4),(61,'2013-04-24 09:40:23',0,230,0,'2013-03-01','girdi',17,3),(62,'2013-04-24 09:45:44',1,12,0,'2013-03-04','çıktı',15,3),(63,'2013-04-24 09:45:44',1,3,0,'2013-03-18','çıktı',15,3),(64,'2013-04-24 09:44:06',0,9,0,'2013-03-25','çıktı',15,3),(65,'2013-04-24 09:44:06',0,3,0,'2013-04-08','çıktı',15,3),(66,'2013-04-24 09:44:06',0,6,0,'2013-04-17','çıktı',15,3),(67,'2013-04-24 09:50:36',0,27,0,'2013-03-01','girdi',18,3),(68,'2013-04-24 09:50:54',1,3,0,'2013-03-18','çıktı',18,3),(69,'2013-04-24 09:50:36',0,3,0,'2013-03-18','çıktı',18,3),(70,'2013-04-24 09:50:36',0,3,0,'2013-04-08','çıktı',18,3),(71,'2013-04-24 09:50:36',0,6,0,'2013-04-17','çıktı',18,3),(72,'2013-04-24 09:58:55',0,27,0,'2013-03-01','girdi',19,3),(73,'2013-04-24 09:58:55',0,6,0,'2013-03-18','çıktı',19,3),(74,'2013-04-24 09:58:55',0,9,0,'2013-03-25','çıktı',19,3),(75,'2013-04-24 09:58:55',0,6,0,'2013-04-08','çıktı',19,3),(76,'2013-04-24 10:00:23',0,44,0,'2013-03-01','girdi',21,3),(77,'2013-04-24 10:00:23',0,4,0,'2013-03-04','çıktı',21,3),(78,'2013-04-24 10:00:23',0,4,0,'2013-03-18','çıktı',21,3),(79,'2013-04-24 10:02:23',0,15,0,'2013-03-01','girdi',20,3),(80,'2013-04-24 10:02:23',0,3,0,'2013-03-04','çıktı',20,3),(81,'2013-04-24 10:02:23',0,3,0,'2013-04-08','çıktı',20,3),(82,'2013-04-24 10:02:23',0,3,0,'2013-04-17','çıktı',20,3),(83,'2013-04-24 10:05:03',0,102,0,'2013-03-01','girdi',22,3),(84,'2013-04-24 10:05:03',0,6,0,'2013-03-04','çıktı',22,3),(85,'2013-04-24 10:05:03',0,6,0,'2013-03-18','çıktı',22,3),(86,'2013-04-24 10:05:03',0,12,0,'2013-03-25','çıktı',22,3),(87,'2013-04-24 10:05:03',0,6,0,'2013-04-08','çıktı',22,3),(88,'2013-04-24 10:05:03',0,12,0,'2013-04-17','çıktı',22,3),(89,'2013-04-24 10:11:57',1,163,0,'2013-03-01','girdi',23,3),(90,'2013-04-26 07:33:09',0,56,0,'2013-03-04','girdi',54,3),(91,'2013-04-26 07:34:34',0,240,0,'2013-03-01','girdi',53,3),(92,'2013-04-26 07:34:34',0,32,0,'2013-04-04','çıktı',53,3),(93,'2013-04-26 07:35:00',0,192,0,'2013-03-01','girdi',52,3),(94,'2013-04-26 07:35:00',0,32,0,'2013-04-04','çıktı',52,3),(95,'2013-04-26 07:35:21',0,36,0,'2013-04-02','girdi',51,3),(96,'2013-04-26 07:35:58',0,720,0,'2013-04-02','girdi',50,3),(97,'2013-04-26 07:36:17',0,900,0,'2013-04-02','girdi',49,3),(98,'2013-04-26 07:40:00',1,504,0,'2013-03-01','girdi',48,3),(99,'2013-04-26 07:40:00',1,24,0,'2013-04-01','çıktı',48,3),(100,'2013-04-26 07:37:28',0,30,0,'2013-03-01','girdi',47,3),(101,'2013-04-26 07:38:07',0,54,0,'2013-03-01','girdi',46,3),(102,'2013-04-26 07:38:49',0,120,0,'2013-03-01','girdi',44,3),(103,'2013-04-26 07:38:50',0,50,0,'2013-04-01','çıktı',44,3),(104,'2013-04-26 07:39:14',0,40,0,'2013-03-01','girdi',45,3),(105,'2013-04-26 07:40:40',0,30,0,'2013-03-01','girdi',43,3),(106,'2013-04-26 07:40:41',0,12,0,'2013-04-04','çıktı',43,3),(107,'2013-04-26 07:41:27',0,84,0,'2013-03-01','girdi',42,3),(108,'2013-04-26 07:41:27',0,12,0,'2013-04-04','çıktı',42,3),(109,'2013-04-26 07:42:11',0,96,0,'2013-03-01','girdi',41,3),(110,'2013-04-26 07:42:11',0,12,0,'2013-03-05','çıktı',41,3),(111,'2013-04-26 07:42:11',0,12,0,'2013-04-04','çıktı',41,3),(112,'2013-04-26 07:42:51',0,78,0,'2013-03-01','girdi',40,3),(113,'2013-04-26 07:42:51',0,6,0,'2013-03-05','çıktı',40,3),(114,'2013-04-26 07:42:51',0,6,0,'2013-04-04','çıktı',40,3),(115,'2013-04-26 07:43:30',0,70,0,'2013-03-01','girdi',39,3),(116,'2013-04-26 07:44:19',0,264,0,'2013-03-01','girdi',38,3),(117,'2013-04-26 07:44:19',0,120,0,'2013-04-01','çıktı',38,3),(118,'2013-04-26 07:45:09',0,120,0,'2013-03-01','girdi',37,3),(119,'2013-04-26 07:45:09',0,40,0,'2013-03-05','çıktı',37,3),(120,'2013-04-26 07:45:09',0,20,0,'2013-04-04','çıktı',37,3),(121,'2013-04-26 07:46:32',0,300,0,'2013-03-01','girdi',36,3),(122,'2013-04-26 07:46:32',0,40,0,'2013-03-05','çıktı',36,3),(123,'2013-04-26 07:46:32',0,60,0,'2013-03-20','çıktı',36,3),(124,'2013-04-26 07:46:32',0,20,0,'2013-04-04','çıktı',36,3),(125,'2013-04-26 07:47:20',0,20,0,'2013-04-04','girdi',34,3),(126,'2013-04-26 07:48:01',0,2016,0,'2013-04-10','girdi',33,3),(127,'2013-04-26 07:54:45',0,96,0,'2013-04-10','girdi',32,3),(128,'2013-04-26 07:55:22',0,72,0,'2013-03-21','girdi',31,3),(129,'2013-04-26 07:55:22',0,6,0,'2013-04-08','çıktı',31,3),(130,'2013-04-26 07:55:59',0,918,0,'2013-03-01','girdi',30,3),(131,'2013-04-26 07:56:23',0,120,0,'2013-03-01','girdi',29,3),(132,'2013-04-26 07:56:52',0,78,0,'2013-03-01','girdi',28,3),(133,'2013-04-26 07:57:47',0,144,0,'2013-03-01','girdi',27,3),(134,'2013-04-26 07:58:05',0,84,0,'2013-03-01','girdi',26,3),(135,'2013-04-26 07:58:26',0,200,0,'2013-03-01','girdi',25,3),(136,'2013-04-26 07:58:46',0,72,0,'2013-04-02','girdi',24,3),(137,'2013-04-26 09:45:48',0,120,0,'2013-03-01','girdi',35,3),(138,'2013-04-26 09:45:48',0,40,0,'2013-03-05','çıktı',35,3),(139,'2013-04-26 09:45:48',0,40,0,'2013-04-04','çıktı',35,3),(140,'2013-04-27 03:03:34',0,192,0,'2013-03-01','girdi',98,5),(141,'2013-04-27 03:04:16',0,96,0,'2013-03-01','girdi',97,5),(142,'2013-04-27 03:04:39',0,96,0,'2013-03-01','girdi',96,5),(143,'2013-04-27 03:05:05',0,120,0,'2013-03-01','girdi',95,5),(144,'2013-04-27 03:05:35',0,90,0,'2013-03-01','girdi',94,5),(145,'2013-04-27 03:05:58',0,24,0,'2013-03-01','girdi',93,5),(146,'2013-04-27 03:06:16',0,32,0,'2013-03-01','girdi',92,5),(147,'2013-04-27 03:06:35',0,96,0,'2013-03-01','girdi',91,5),(148,'2013-04-27 03:06:52',0,96,0,'2013-03-01','girdi',90,5),(149,'2013-04-27 03:07:14',0,120,0,'2013-03-01','girdi',89,5),(150,'2013-04-27 03:07:35',0,96,0,'2013-03-01','girdi',88,5),(151,'2013-04-27 03:09:26',0,64,0,'2013-03-01','girdi',87,5),(152,'2013-04-27 03:09:46',0,36,0,'2013-03-01','girdi',86,5),(153,'2013-04-27 03:10:16',0,12,0,'2013-03-01','girdi',85,5),(154,'2013-04-27 03:10:35',0,32,0,'2013-03-01','girdi',84,5),(155,'2013-04-27 03:10:57',0,36,0,'2013-03-01','girdi',83,5),(156,'2013-04-27 03:11:19',0,192,0,'2013-03-01','girdi',82,5),(157,'2013-04-27 03:11:51',0,384,0,'2013-03-01','girdi',81,5),(158,'2013-04-27 03:13:54',1,72,911424,'2013-03-07','girdi',80,5),(159,'2013-04-27 03:13:35',1,64,911424,'2013-03-07','girdi',79,5),(160,'2013-04-27 03:13:22',1,64,911424,'2013-03-07','girdi',78,5),(161,'2013-04-27 03:14:42',0,480,911681,'2013-03-21','girdi',31,5),(162,'2013-04-27 03:15:41',0,128,0,'2013-03-01','girdi',76,5),(163,'2013-04-27 03:16:14',0,76,0,'2013-03-04','girdi',75,5),(164,'2013-04-27 03:17:02',0,90,911422,'2013-03-07','girdi',74,5),(165,'2013-04-27 03:17:21',1,30,0,'2013-03-11','çıktı',74,5),(166,'2013-04-27 03:18:02',0,210,911422,'2013-03-07','girdi',73,5),(167,'2013-04-27 03:18:02',0,30,0,'2013-03-11','çıktı',73,5),(168,'2013-04-27 03:18:45',0,440,0,'2013-03-04','girdi',72,5),(169,'2013-04-27 03:18:45',0,120,0,'2013-04-01','çıktı',72,5),(170,'2013-04-27 03:23:40',3,745,0,'2013-03-04','girdi',66,5),(171,'2013-04-27 03:31:44',1,94,0,'2013-03-04','girdi',64,5),(172,'2013-04-27 03:37:33',1,294,0,'2013-03-04','girdi',62,5),(173,'2013-04-27 03:36:15',1,556,0,'2013-03-04','girdi',63,5),(174,'2013-04-27 03:29:50',1,122,0,'2013-03-04','girdi',65,5),(175,'2013-04-27 03:23:40',1,18,0,'2013-03-07','çıktı',66,5),(177,'2013-04-27 03:23:40',1,27,0,'2013-04-01','çıktı',66,5),(178,'2013-04-27 03:29:50',0,4,0,'2013-03-01','çıktı',65,5),(179,'2013-04-27 03:29:50',0,10,0,'2013-03-07','çıktı',65,5),(180,'2013-04-27 03:29:50',0,10,0,'2013-04-01','çıktı',65,5),(181,'2013-04-27 03:31:44',0,10,0,'2013-03-07','çıktı',64,5),(182,'2013-04-27 03:31:44',0,8,0,'2013-04-01','çıktı',64,5),(183,'2013-04-27 03:36:37',0,12,0,'2013-03-07','çıktı',63,5),(184,'2013-04-27 03:36:37',0,16,0,'2013-04-01','çıktı',63,5),(185,'2013-04-27 03:37:33',0,10,0,'2013-03-07','çıktı',62,5),(186,'2013-04-27 03:37:33',0,8,0,'2013-04-01','çıktı',62,5),(187,'2013-04-27 03:40:26',0,405,0,'2013-03-01','girdi',61,5),(188,'2013-04-27 03:40:27',0,9,0,'2013-04-05','çıktı',61,5),(189,'2013-04-27 03:40:27',0,12,0,'2013-03-11','çıktı',61,5),(190,'2013-04-27 03:40:27',0,6,0,'2013-03-20','çıktı',61,5),(191,'2013-04-27 03:40:27',0,6,0,'2013-04-01','çıktı',61,5),(192,'2013-04-27 03:40:27',0,12,0,'2013-04-11','çıktı',61,5),(193,'2013-04-27 03:41:04',1,6,0,'2013-04-22','çıktı',61,5),(194,'2013-04-27 03:42:10',1,30,911422,'2013-03-07','girdi',60,5),(195,'2013-04-27 03:42:01',0,30,911422,'2013-03-07','girdi',59,5),(196,'2013-04-27 03:42:45',0,24,911422,'2013-03-07','girdi',58,5),(197,'2013-04-27 03:43:18',0,24,911422,'2013-03-07','girdi',57,5),(198,'2013-04-27 03:43:57',0,650,911683,'2013-03-21','girdi',56,5),(199,'2013-04-27 03:44:40',0,80,911424,'2013-03-07','girdi',55,5);
/*!40000 ALTER TABLE `warehouse_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-27 11:50:30
